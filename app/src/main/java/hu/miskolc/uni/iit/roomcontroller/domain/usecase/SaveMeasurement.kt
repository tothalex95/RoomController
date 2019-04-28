package hu.miskolc.uni.iit.roomcontroller.domain.usecase

import hu.miskolc.uni.iit.roomcontroller.data.repository.MeasurementRepository
import hu.miskolc.uni.iit.roomcontroller.domain.model.Measurement
import org.json.JSONObject

class SaveMeasurement(
        private val messageRepository: MeasurementRepository
) : ObservableUseCase<JSONObject, Measurement>() {

    override fun execute(params: Measurement) = messageRepository.saveMeasurement(params)

}
