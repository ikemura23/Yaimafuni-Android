package com.yaeyama.linerchecker.domain.repository

import com.yaeyama.linerchecker.domain.typhoon.Typhoon
import kotlinx.coroutines.flow.Flow

interface TyphoonRepository {
    fun fetchTyphoonList(): Flow<List<Typhoon>>
}
