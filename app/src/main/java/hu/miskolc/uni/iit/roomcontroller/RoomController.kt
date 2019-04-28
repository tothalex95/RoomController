package hu.miskolc.uni.iit.roomcontroller

import android.app.Application
import hu.miskolc.uni.iit.roomcontroller.api.MessageApiService
import hu.miskolc.uni.iit.roomcontroller.data.repository.MeasurementRepository
import hu.miskolc.uni.iit.roomcontroller.data.repository.MeasurementRepositoryImpl
import hu.miskolc.uni.iit.roomcontroller.data.repository.ServoRepository
import hu.miskolc.uni.iit.roomcontroller.data.repository.ServoRepositoryImpl
import hu.miskolc.uni.iit.roomcontroller.domain.usecase.GetMeasurement
import hu.miskolc.uni.iit.roomcontroller.domain.usecase.GetServoPosition
import hu.miskolc.uni.iit.roomcontroller.domain.usecase.SaveMeasurement
import hu.miskolc.uni.iit.roomcontroller.domain.usecase.SetServoPosition
import hu.miskolc.uni.iit.roomcontroller.presentation.viewmodel.MyViewModelFactory
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module

class RoomController : Application() {

    override fun onCreate() {
        super.onCreate()

        val appModule = module {
            single { MessageApiService.create() }
            single<MeasurementRepository> { MeasurementRepositoryImpl(get()) }
            single<ServoRepository> { ServoRepositoryImpl(get()) }
            single { SaveMeasurement(get()) }
            single { SetServoPosition(get()) }
            single { GetMeasurement(get()) }
            single { GetServoPosition(get()) }
            single { MyViewModelFactory(get(), get(), get(), get()) }
        }

        startKoin {
            androidLogger()
            androidContext(this@RoomController)
            modules(appModule)
        }
    }

}
