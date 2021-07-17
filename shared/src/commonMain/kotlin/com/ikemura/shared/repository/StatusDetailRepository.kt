package com.ikemura.shared.repository

import com.ikemura.shared.model.statusdetail.Company
import com.ikemura.shared.model.statusdetail.PortStatus
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.app
import dev.gitlive.firebase.database.database
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class StatusDetailRepository {
    /**
     * 運行情報の詳細を取得する
     */
    @InternalCoroutinesApi
    fun fetchStatusDetail(company: Company, portCode: String): Flow<UiState> = flow {
        val path = "${company.code}/$portCode"
        val dbRef = Firebase.database(Firebase.app).reference(path)
        dbRef.valueEvents.collect {
            val portStatus = it.value(PortStatus.serializer())
            emit(UiState.Success(portStatus))
        }
    }

    sealed class UiState {
        data class Success(val data: PortStatus) : UiState()
        data class Error(val error: Throwable) : UiState()
    }
}
