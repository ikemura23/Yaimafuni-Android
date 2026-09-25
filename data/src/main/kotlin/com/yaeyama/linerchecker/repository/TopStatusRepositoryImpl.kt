package com.yaeyama.linerchecker.repository

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.getValue
import com.yaeyama.linerchecker.domain.repository.TopStatusRepository
import com.yaeyama.linerchecker.domain.top.Ports
import com.yaeyama.linerchecker.domain.top.TopPort
import com.yaeyama.linerchecker.ext.valueEvents
import kotlinx.coroutines.flow.Flow

class TopStatusRepositoryImpl(
    private val database: FirebaseDatabase,
) : TopStatusRepository {

    override fun fetchTopStatuses(): Flow<List<Ports>> =
        database.valueEvents("top_port") { snapshot ->
            snapshot.getValue<TopPort>()?.toList()
        }

    private fun TopPort.toList(): List<Ports> = listOf(
        this.taketomi,
        this.kohama,
        this.kuroshima,
        this.oohara,
        this.uehara,
        this.hatoma,
        this.hateruma,
    )
}
