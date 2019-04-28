package hu.miskolc.uni.iit.roomcontroller.api

import hu.miskolc.uni.iit.roomcontroller.domain.model.Measurement
import hu.miskolc.uni.iit.roomcontroller.domain.model.ServoPosition
import io.reactivex.Observable
import okhttp3.OkHttpClient
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MessageApiService {

    companion object {
        fun create(): MessageApiService {
            return Retrofit.Builder()
                    .baseUrl("https://us-central1-roomcontroller-94911.cloudfunctions.net")
                    .client(OkHttpClient.Builder().build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
                    .create(MessageApiService::class.java)
        }
    }

    @GET("/lastMeasurement")
    fun getLastMeasurement(): Observable<Measurement>

    @POST("/saveMeasurement")
    fun saveMeasurement(
            @Body measurement: Measurement
    ): Observable<JSONObject>

    @GET("/getServoPosition")
    fun getServoPosition(): Observable<ServoPosition>

    @POST("/setServoPosition")
    fun setServoPosition(
            @Body servoPosition: ServoPosition
    ): Observable<JSONObject>

}
