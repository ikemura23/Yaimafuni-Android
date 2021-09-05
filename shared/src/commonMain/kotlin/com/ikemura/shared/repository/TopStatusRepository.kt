package com.ikemura.shared.repository

import com.ikemura.shared.model.top.Ports
import kotlinx.coroutines.flow.Flow

/**
 * トップ画面用の運行ステータスを取得するRepository
 */
interface TopStatusRepository {
    fun fetchTopStatuses(): Flow<UiState<List<Ports>>>
}