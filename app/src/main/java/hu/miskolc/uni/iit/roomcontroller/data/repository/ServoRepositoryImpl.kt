package hu.miskolc.uni.iit.roomcontroller.data.repository

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import hu.miskolc.uni.iit.roomcontroller.api.MessageApiService
import hu.miskolc.uni.iit.roomcontroller.domain.model.ServoPosition
import io.reactivex.Observable

class ServoRepositoryImpl(
        private val messageApiService: MessageApiService
) : ServoRepository {

    private val databaseReference = FirebaseDatabase.getInstance().getReference("/servoPositions")

    override fun getServoPosition(): Observable<ServoPosition> {
        return Observable.create {
            databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val lastIndex = dataSnapshot.children.indexOfLast { true }
                    val last = dataSnapshot.children.elementAt(lastIndex).getValue(ServoPosition::class.java)
                    it.onNext(last ?: ServoPosition())
                    it.onComplete()
                }

                override fun onCancelled(databaseError: DatabaseError) = Unit
            })
        }
    }

    override fun setServoPosition(servoPosition: ServoPosition) = messageApiService.setServoPosition(servoPosition)

}
