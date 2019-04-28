package hu.miskolc.uni.iit.roomcontroller.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import hu.miskolc.uni.iit.roomcontroller.domain.usecase.GetMeasurement
import hu.miskolc.uni.iit.roomcontroller.domain.usecase.GetServoPosition
import hu.miskolc.uni.iit.roomcontroller.domain.usecase.SaveMeasurement
import hu.miskolc.uni.iit.roomcontroller.domain.usecase.SetServoPosition

class MyViewModelFactory(
        private val saveMeasurement: SaveMeasurement,
        private val setServoPosition: SetServoPosition,
        private val getMeasurement: GetMeasurement,
        private val getServoPosition: GetServoPosition
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MeasurementViewModel::class.java) -> MeasurementViewModel(saveMeasurement, getMeasurement) as T
            modelClass.isAssignableFrom(ServoViewModel::class.java) -> ServoViewModel(setServoPosition, getServoPosition) as T
            else -> throw IllegalArgumentException("Unknown view model: ${modelClass.simpleName}")
        }
    }

}
