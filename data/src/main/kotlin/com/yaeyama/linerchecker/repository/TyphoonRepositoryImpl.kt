package com.yaeyama.linerchecker.repository

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.getValue
import com.yaeyama.linerchecker.ext.valueEvents
import com.yaeyama_liner_checker.domain.repository.TyphoonRepository
import com.yaeyama_liner_checker.domain.typhoon.Typhoon
import kotlinx.coroutines.flow.Flow

class TyphoonRepositoryImpl(
    private val database: FirebaseDatabase,
) : TyphoonRepository {

    override fun fetchTyphoonList(): Flow<List<Typhoon>> =
        database.valueEvents("typhoon/tenkijp") { snapshot ->
            // 台風が発生していないときはデータが無いため、空リストとして扱う
            snapshot.getValue<List<Typhoon>>() ?: listOf()
        }
}
