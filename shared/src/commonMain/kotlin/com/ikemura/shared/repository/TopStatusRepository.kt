package com.ikemura.shared.repository

import com.ikemura.shared.model.top.Ports
import com.ikemura.shared.model.top.TopPort
import kotlinx.coroutines.flow.Flow

/**
 * トップ画面用の運行ステータスを取得するRepository
 */
interface TopStatusRepository {
    fun fetchTopStatus(): Flow<UiState<TopPort>>
    fun fetchTopStatuses(): Flow<UiState<List<Ports>>>
}