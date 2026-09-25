package com.yaeyama.linerchecker.domain.repository

import com.yaeyama.linerchecker.domain.top.TopPort
import kotlinx.coroutines.flow.Flow

/**
 * トップ画面用の運行ステータスを取得するRepository
 */
interface TopStatusRepository {
    fun fetchTopStatuses(): Flow<TopPort>
}
