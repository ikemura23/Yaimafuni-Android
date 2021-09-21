package com.ikemura.shared.repository

import com.ikemura.shared.model.tyhoon.Typhoon
import dev.gitlive.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class TyphoonRepositoryImpl : TyphoonRepository, KoinComponent {

    private val database: FirebaseDatabase by inject()

    override fun fetchTyphoonList(): Flow<List<Typhoon>> {
        val dbRef = database.reference("typhoon/tenkijp")
        return dbRef.valueEvents.map { snapshot ->
            snapshot.value() as List<Typhoon>
        }.catch {
            listOf<List<Typhoon>>()
        }
    }
}