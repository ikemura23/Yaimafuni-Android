package com.yaeyama.linerchecker.repository

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.getValue
import com.yaeyama.linerchecker.ext.reference
import com.yaeyama.linerchecker.ext.valueEvents
import com.yaeyama_liner_checker.domain.repository.TyphoonRepository
import com.yaeyama_liner_checker.domain.typhoon.Typhoon
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TyphoonRepositoryImpl(
    private val database: FirebaseDatabase,
) : TyphoonRepository {


    override fun fetchTyphoonList(): Flow<List<Typhoon>> {
        val dbRef = database.reference("typhoon/tenkijp")
        return dbRef.valueEvents.map { snapshot: DataSnapshot ->
            snapshot.getValue<List<Typhoon>>() ?: listOf()
        }
    }
}
