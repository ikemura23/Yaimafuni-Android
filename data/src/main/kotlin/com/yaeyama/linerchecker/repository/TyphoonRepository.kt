package com.yaeyama.linerchecker.repository

import com.yaeyama_liner_checker.domain.tyhoon.Typhoon
import kotlinx.coroutines.flow.Flow

interface TyphoonRepository {
    fun fetchTyphoonList(): Flow<List<Typhoon>>
}
