package data.repository

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.ikemura.shared.repository.UiState
import com.yaeyama_liner_checker.domain.statusdetail.Company
import com.yaeyama_liner_checker.domain.statusdetail.PortStatus
import com.yaeyama_liner_checker.domain.time_table.TimeTable
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class StatusDetailRepository : KoinComponent {
    // Firebase Realtime Database
    private val database: FirebaseDatabase by inject()

    /**
     * 運行情報の詳細を取得する
     */
    fun fetchStatusDetail(company: Company, portCode: String): Flow<UiState<PortStatus>> {
        val path = "${company.code}/$portCode"
        val dbRef = database.getReference(path)
        return callbackFlow<UiState<PortStatus>> {
            val listener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val data = snapshot.getValue<PortStatus>()

                    data?.let {
                        trySend(UiState.Success(data))
                    } ?: trySend(UiState.Error(Exception("nullになったよ")))
                }

                override fun onCancelled(error: DatabaseError) {
                    trySend(UiState.Error(error.toException()))
                    // close(error.toException())
                }
            }
            dbRef.addValueEventListener(listener)
            awaitClose {
                dbRef.removeEventListener(listener)
            }
        }
        // return dbRef.valueEvents
        //     .map {
        //         val deserializeValue = it.value(PortStatus.serializer())
        //         UiState.Success(deserializeValue)
        //     }
        //     .catch { UiState.Error(it) }
    }

    fun fetchTimeTable(company: Company, portCode: String): Flow<UiState<TimeTable>> {
        val path = "${company.code}_timeTable/$portCode"
        val dbRef = database.getReference(path)
        return callbackFlow<UiState<TimeTable>> {
            val listener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val data = snapshot.getValue(TimeTable::class.java)

                    data?.let {
                        trySend(UiState.Success(data))
                    } ?: trySend(UiState.Error(Exception("nullになったよ")))
                }

                override fun onCancelled(error: DatabaseError) {
                    trySend(UiState.Error(error.toException()))
                    // close(error.toException())
                }
            }
            dbRef.addValueEventListener(listener)
            awaitClose {
                dbRef.removeEventListener(listener)
            }
        }
        // return dbRef.valueEvents
        //     .map {
        //         val deserializeValue = it.value(TimeTable.serializer())
        //         UiState.Success(deserializeValue)
        //     }
        //     .catch { UiState.Error(it) }
    }
}
