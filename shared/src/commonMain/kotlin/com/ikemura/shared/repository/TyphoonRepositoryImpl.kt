package com.ikemura.shared.repository

import com.ikemura.shared.model.tyhoon.Typhoon
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TyphoonRepositoryImpl : TyphoonRepository {

    override fun fetchTyphoonList(): Flow<List<Typhoon>> {
        return flow { listOf<Typhoon>() } // TODO: 仮の戻り値
    }
}