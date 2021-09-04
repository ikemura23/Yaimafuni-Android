package com.ikemura.shared.repository

import com.ikemura.shared.model.statusdetail.Company
import com.ikemura.shared.model.statusdetail.PortStatus
import com.ikemura.shared.model.time_table.TimeTable
import dev.gitlive.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
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
        val dbRef = database.reference(path)
        return dbRef.valueEvents
            .map {
                val deserializeValue = it.value(PortStatus.serializer())
                UiState.Success(deserializeValue)
            }
            .catch { UiState.Error(it) }
    }

    fun fetchTimeTable(company: Company, portCode: String): Flow<UiState<TimeTable>> {
        val path = "${company.code}_timeTable/$portCode"
        val dbRef = database.reference(path)
        return dbRef.valueEvents
            .map {
                val deserializeValue = it.value(TimeTable.serializer())
                UiState.Success(deserializeValue)
            }
            .catch { UiState.Error(it) }
    }
}
