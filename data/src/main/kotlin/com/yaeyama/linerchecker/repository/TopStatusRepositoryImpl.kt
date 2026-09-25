package com.yaeyama.linerchecker.repository

import com.google.firebase.database.FirebaseDatabase
import com.yaeyama.linerchecker.domain.repository.TopStatusRepository
import com.yaeyama.linerchecker.domain.top.TopPort
import com.yaeyama.linerchecker.ext.valueEventsOf
import kotlinx.coroutines.flow.Flow

class TopStatusRepositoryImpl(
    private val database: FirebaseDatabase,
) : TopStatusRepository {

    override fun fetchTopStatuses(): Flow<TopPort> = database.valueEventsOf<TopPort>("top_port")
}
