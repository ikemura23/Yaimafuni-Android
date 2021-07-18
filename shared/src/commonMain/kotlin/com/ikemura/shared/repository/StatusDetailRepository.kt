package com.ikemura.shared.repository

import com.ikemura.shared.model.statusdetail.Company
import com.ikemura.shared.model.statusdetail.PortStatus
import com.ikemura.shared.model.time_table.TimeTable
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.app
import dev.gitlive.firebase.database.FirebaseDatabase
import dev.gitlive.firebase.database.database
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class StatusDetailRepository {
    // Firebase Realtime Database
    private var database: FirebaseDatabase = Firebase.database(Firebase.app)

    /**
     * 運行情報の詳細を取得する
     */
    fun fetchStatusDetail(company: Company, portCode: String): Flow<UiState<PortStatus>> = flow {
        val path = "${company.code}/$portCode"
        val dbRef = database.reference(path)
        dbRef.valueEvents
            .map {
                val deserializeValue = it.value(PortStatus.serializer())
                UiState.Success(deserializeValue)
            }
            .catch { UiState.Error(it) }
    }

    fun fetchTimeTable(company: Company, portCode: String): Flow<UiState<TimeTable>> = flow {
        val path = "${company.code}_timeTable/$portCode"
        val dbRef = database.reference(path)
        dbRef.valueEvents
            .map {
                val deserializeValue = it.value(TimeTable.serializer())
                UiState.Success(deserializeValue)
            }
            .catch { UiState.Error(it) }
    }
}
