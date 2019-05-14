package hu.miskolc.uni.iit.roomcontroller.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import hu.miskolc.uni.iit.roomcontroller.domain.model.Measurement
import hu.miskolc.uni.iit.roomcontroller.domain.usecase.GetMeasurement
import hu.miskolc.uni.iit.roomcontroller.domain.usecase.SaveMeasurement

class MeasurementViewModel(
    private val saveMeasurement: SaveMeasurement,
    private val getMeasurement: GetMeasurement
) : BaseViewModel() {

    private val measurement: MutableLiveData<Measurement> by lazy {
        MutableLiveData<Measurement>().also {
            refreshMeasurement()
        }
    }

    fun measurement(): LiveData<Measurement> = measurement

    fun refreshMeasurement() {
        getMeasurement(
                onSuccess = {measurement.value = it},
                onError = {},
                params = Any()
        )
    }

}
