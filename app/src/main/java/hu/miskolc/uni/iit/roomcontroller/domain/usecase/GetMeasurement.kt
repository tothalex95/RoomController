package hu.miskolc.uni.iit.roomcontroller.domain.usecase

import hu.miskolc.uni.iit.roomcontroller.data.repository.MeasurementRepository
import hu.miskolc.uni.iit.roomcontroller.domain.model.Measurement

class GetMeasurement(
        private val measurementRepository: MeasurementRepository
) : ObservableUseCase<Measurement, Any>() {

    override fun execute(params: Any) = measurementRepository.getMeasurement()

}
