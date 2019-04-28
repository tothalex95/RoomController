package hu.miskolc.uni.iit.roomcontroller

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.RemoteMessage
import hu.miskolc.uni.iit.roomcontroller.domain.model.ServoPosition
import hu.miskolc.uni.iit.roomcontroller.presentation.viewmodel.MeasurementViewModel
import hu.miskolc.uni.iit.roomcontroller.presentation.viewmodel.MyViewModelFactory
import hu.miskolc.uni.iit.roomcontroller.presentation.viewmodel.ServoViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity(), LifecycleOwner {

    companion object {
        private const val TAG = "MainActivity"
    }

    private val lifecycleRegistry = LifecycleRegistry(this)

    private val viewModelFactory by inject<MyViewModelFactory>()

    private val viewModelProvider by lazy {
        ViewModelProviders.of(this, viewModelFactory)
    }

    private val measurementViewModel by lazy {
        viewModelProvider.get(MeasurementViewModel::class.java)
    }

    private val servoViewModel by lazy {
        viewModelProvider.get(ServoViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setupNotificationChannel()
        }

        intent.extras?.let {
            it.keySet().forEach { key ->
                Log.i(TAG, "Key: $key, value: ${intent?.extras?.get(key)}")
            }
        }

        FirebaseInstanceId.getInstance().instanceId.addOnCompleteListener(OnCompleteListener {
            if (!it.isSuccessful) {
                Log.i(TAG, "getInstanceId failed", it.exception)
                return@OnCompleteListener
            }

            Log.i(TAG, it.result?.token)
        })

        subscribeButton.setOnClickListener(subscribeButtonOnClickListener)
        unsubscribeButton.setOnClickListener(unsubscribeButtonOnClickListener)

        servoPositionSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) = Unit
            override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                servoViewModel.changeServoPosition(ServoPosition(seekBar?.progress?.times(30) ?: 0))
            }
        })

        temperatureSeekBar.isEnabled = false

        measurementViewModel.measurement().observe(this, Observer {
            humidityProgressBar.progress = it.humidity
            temperatureSeekBar.progress = it.temperature

            humidityTextView.text = getString(R.string.humidityTitle, it.humidity)
            temperatureTextView.text = getString(R.string.temperatureTitle, it.temperature)
        })

        servoViewModel.servoPosition().observe(this, Observer {
            servoPositionSeekBar.progress = it.servoPosition / 30
            servoPositionTextView.text = getString(R.string.servoPositionTitle, it.servoPosition)
        })
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun setupNotificationChannel() {
        getSystemService(NotificationManager::class.java).createNotificationChannel(
                NotificationChannel(
                        "MyChannel",
                        "MyChannelName",
                        NotificationManager.IMPORTANCE_DEFAULT
                )
        )
    }

    private val subscribeButtonOnClickListener = View.OnClickListener {
        FirebaseMessaging.getInstance().subscribeToTopic("measurements")
                .addOnCompleteListener {
                    if (!it.isSuccessful) {
                        Toast.makeText(
                                applicationContext,
                                R.string.subscribeFail,
                                Toast.LENGTH_LONG
                        ).show()
                        return@addOnCompleteListener
                    }

                    Toast.makeText(
                            applicationContext,
                            R.string.subscribeSuccess,
                            Toast.LENGTH_LONG
                    ).show()
                }
    }

    private val unsubscribeButtonOnClickListener = View.OnClickListener {
        FirebaseMessaging.getInstance().unsubscribeFromTopic("measurements")
                .addOnCompleteListener {
                    if (!it.isSuccessful) {
                        Toast.makeText(
                                applicationContext,
                                R.string.unsubscribeFail,
                                Toast.LENGTH_LONG
                        ).show()
                        return@addOnCompleteListener
                    }

                    Toast.makeText(
                            applicationContext,
                            R.string.unsubscribeSuccess,
                            Toast.LENGTH_LONG
                    ).show()
                }
    }

    override fun getLifecycle(): Lifecycle {
        return lifecycleRegistry
    }

}
