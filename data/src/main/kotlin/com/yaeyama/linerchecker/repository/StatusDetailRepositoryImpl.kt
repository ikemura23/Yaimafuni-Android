package com.yaeyama.linerchecker.repository

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.yaeyama_liner_checker.domain.statusdetail.Company
import com.yaeyama_liner_checker.domain.statusdetail.PortStatus
import com.yaeyama_liner_checker.domain.time_table.TimeTable
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class StatusDetailRepositoryImpl : StatusDetailRepository, KoinComponent {
    // Firebase Realtime Database
    private val database: FirebaseDatabase by inject()

    /**
     * 運行情報の詳細を取得する
     */
    override fun fetchStatusDetail(company: Company, portCode: String): Flow<PortStatus> {
        val path = "${company.code}/$portCode"
        val dbRef = database.getReference(path)
        return callbackFlow<PortStatus> {
            val listener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val data = snapshot.getValue<PortStatus>()

                    data?.let {
                        trySend(data)
                    } ?: throw Exception("nullになったよ")
                }

                override fun onCancelled(error: DatabaseError) {
                    throw error.toException()
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

    override fun fetchTimeTable(company: Company, portCode: String): Flow<TimeTable> {
        val path = "${company.code}_timeTable/$portCode"
        val dbRef = database.getReference(path)
        return callbackFlow<TimeTable> {
            val listener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val data = snapshot.getValue(TimeTable::class.java)

                    data?.let {
                        trySend(data)
                    } ?: throw Exception("nullになったよ")
                }

                override fun onCancelled(error: DatabaseError) {
                    throw error.toException()
                    // trySend(UiState.Error(error.toException()))
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
