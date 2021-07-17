package com.ikemura.shared.repository

import com.ikemura.shared.model.statusdetail.PortStatus

class StatusDetailRepository {
    /**
     * 運行情報の詳細を取得する
     */
    fun fetchStatusDetail() {
    }

    sealed class UiState {
        data class Success(val data: PortStatus) : UiState()
        data class Error(val error: Throwable) : UiState()
    }
}
