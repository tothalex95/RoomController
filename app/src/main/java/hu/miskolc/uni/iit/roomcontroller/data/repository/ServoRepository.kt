package hu.miskolc.uni.iit.roomcontroller.data.repository

import hu.miskolc.uni.iit.roomcontroller.domain.model.ServoPosition
import io.reactivex.Observable
import org.json.JSONObject

interface ServoRepository {

    fun getServoPosition(): Observable<ServoPosition>

    fun setServoPosition(
            servoPosition: ServoPosition
    ): Observable<JSONObject>

}
