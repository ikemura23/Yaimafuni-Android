package com.ikemura.shared.repository

import com.ikemura.shared.model.tyhoon.Typhoon
import kotlinx.coroutines.flow.Flow

interface TyphoonRepository {
    fun fetchTyphoonList(): Flow<List<Typhoon>>
}