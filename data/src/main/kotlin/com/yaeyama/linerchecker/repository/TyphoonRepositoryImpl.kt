package com.yaeyama.linerchecker.repository

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.getValue
import com.yaeyama.linerchecker.ext.reference
import com.yaeyama.linerchecker.ext.valueEvents
import com.yaeyama_liner_checker.domain.repository.TyphoonRepository
import com.yaeyama_liner_checker.domain.tyhoon.Typhoon
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class TyphoonRepositoryImpl : TyphoonRepository, KoinComponent {

    private val database: FirebaseDatabase by inject()

    override fun fetchTyphoonList(): Flow<List<Typhoon>> {
        val dbRef = database.reference("typhoon/tenkijp")
        return dbRef.valueEvents.map { snapshot: DataSnapshot ->
            snapshot.getValue<List<Typhoon>>() ?: listOf()
        }.catch {
            it.printStackTrace()
            emit(listOf())
        }
    }
}
