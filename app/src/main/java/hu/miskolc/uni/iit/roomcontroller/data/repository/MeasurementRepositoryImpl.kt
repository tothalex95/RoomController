package hu.miskolc.uni.iit.roomcontroller.data.repository

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import hu.miskolc.uni.iit.roomcontroller.api.MessageApiService
import hu.miskolc.uni.iit.roomcontroller.domain.model.Measurement
import io.reactivex.Observable

class MeasurementRepositoryImpl(
        private val messageApiService: MessageApiService
) : MeasurementRepository {

    private val databaseReference = FirebaseDatabase.getInstance().getReference("/measurements")

    override fun getMeasurement(): Observable<Measurement> {
        return Observable.create {
            databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val lastIndex = dataSnapshot.children.indexOfLast { true }
                    val last = dataSnapshot.children.elementAt(lastIndex).child("measurement").getValue(Measurement::class.java)
                    it.onNext(last ?: Measurement())
                    it.onComplete()
                }

                override fun onCancelled(databaseError: DatabaseError) = Unit
            })
        }
    }

    override fun saveMeasurement(measurement: Measurement) = messageApiService.saveMeasurement(measurement)

}
