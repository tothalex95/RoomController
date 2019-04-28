package hu.miskolc.uni.iit.roomcontroller.api

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import hu.miskolc.uni.iit.roomcontroller.MainActivity

class MyFirebaseMessagingService : FirebaseMessagingService() {

    companion object {
        private const val TAG = "MyFirebaseMsgService"
    }

    override fun onNewToken(token: String?) {
        Log.i(TAG, "Refreshed token: $token")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        Log.i(TAG, "From: ${remoteMessage?.from}")

        remoteMessage?.data?.isNotEmpty()?.let {
            Log.i(TAG, "Message data payload: " + remoteMessage.data)
        }

        remoteMessage?.notification?.let {
            Log.i(TAG, "Message Notification Body: ${it.body}")
        }

        sendNotification()
    }

    private fun sendNotification() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, "MyChannel")
            .setContentTitle("MyContentTitle")
            .setContentText("MyContentText")
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)
            .setColor(Color.CYAN)

        val notificationManager = getSystemService(NotificationManager::class.java)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setupNotificationChannel(notificationManager)
        }

        notificationManager.notify(0, notificationBuilder.build())
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun setupNotificationChannel(notificationManager: NotificationManager) {
        val notificationChannel =
            NotificationChannel("MyChannel", "MyChannelName", NotificationManager.IMPORTANCE_DEFAULT)

        notificationChannel.description = "My notification channel description"
        notificationChannel.enableLights(true)
        notificationChannel.lightColor = Color.CYAN
        notificationChannel.enableVibration(true)

        notificationManager.createNotificationChannel(notificationChannel)
    }

}
