package com.yaeyama.linerchecker.repository

import com.google.firebase.database.FirebaseDatabase
import com.yaeyama.linerchecker.domain.repository.TopStatusRepository
import com.yaeyama.linerchecker.domain.top.Ports
import com.yaeyama.linerchecker.domain.top.TopPort
import com.yaeyama.linerchecker.ext.valueEventsOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TopStatusRepositoryImpl(
    private val database: FirebaseDatabase,
) : TopStatusRepository {

    override fun fetchTopStatuses(): Flow<List<Ports>> =
        database.valueEventsOf<TopPort>("top_port").map { topPort -> topPort.toList() }

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
