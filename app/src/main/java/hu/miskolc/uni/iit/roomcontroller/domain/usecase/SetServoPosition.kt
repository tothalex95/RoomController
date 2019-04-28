package hu.miskolc.uni.iit.roomcontroller.domain.usecase

import hu.miskolc.uni.iit.roomcontroller.data.repository.ServoRepository
import hu.miskolc.uni.iit.roomcontroller.domain.model.ServoPosition
import org.json.JSONObject

class SetServoPosition(
        private val servoRepository: ServoRepository
) : ObservableUseCase<JSONObject, ServoPosition>() {

    override fun execute(params: ServoPosition) = servoRepository.setServoPosition(params)

}
