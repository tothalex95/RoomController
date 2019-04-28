package hu.miskolc.uni.iit.roomcontroller.domain.usecase

import hu.miskolc.uni.iit.roomcontroller.data.repository.ServoRepository
import hu.miskolc.uni.iit.roomcontroller.domain.model.ServoPosition

class GetServoPosition(
        private val servoRepository: ServoRepository
) : ObservableUseCase<ServoPosition, Any>() {

    override fun execute(params: Any) = servoRepository.getServoPosition()

}
