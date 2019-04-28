package hu.miskolc.uni.iit.roomcontroller.data.repository

import hu.miskolc.uni.iit.roomcontroller.domain.model.Measurement
import io.reactivex.Observable
import org.json.JSONObject

interface MeasurementRepository {

    fun getMeasurement(): Observable<Measurement>

    fun saveMeasurement(
            measurement: Measurement
    ): Observable<JSONObject>

}
