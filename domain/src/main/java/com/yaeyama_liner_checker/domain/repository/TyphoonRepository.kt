package com.yaeyama_liner_checker.domain.repository

import com.yaeyama_liner_checker.domain.typhoon.Typhoon
import kotlinx.coroutines.flow.Flow

interface TyphoonRepository {
    fun fetchTyphoonList(): Flow<List<Typhoon>>
}
