package com.yaeyama_liner_checker.domain.repository

import com.yaeyama_liner_checker.domain.top.Ports
import kotlinx.coroutines.flow.Flow

/**
 * トップ画面用の運行ステータスを取得するRepository
 */
interface TopStatusRepository {
    fun fetchTopStatuses(): Flow<List<Ports>>
}
