package hu.miskolc.uni.iit.roomcontroller.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import hu.miskolc.uni.iit.roomcontroller.domain.model.ServoPosition
import hu.miskolc.uni.iit.roomcontroller.domain.usecase.GetServoPosition
import hu.miskolc.uni.iit.roomcontroller.domain.usecase.SetServoPosition

class ServoViewModel(
        private val setServoPosition: SetServoPosition,
        private val getServoPosition: GetServoPosition
) : BaseViewModel(setServoPosition) {

    private val servoPosition: MutableLiveData<ServoPosition> by lazy {
        MutableLiveData<ServoPosition>().also {
            refreshServoPosition()
        }
    }

    fun servoPosition(): LiveData<ServoPosition> = servoPosition

    fun changeServoPosition(servoPosition: ServoPosition) {
        setServoPosition(
                onSuccess = { this.servoPosition.value = servoPosition },
                onError = {},
                params = servoPosition
        )
    }

    fun refreshServoPosition() {
        getServoPosition(
                onSuccess = { servoPosition.value = it },
                onError = {},
                params = Any()
        )
    }

}
