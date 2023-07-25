package com.yaeyama.linerchecker.repository

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.getValue
import com.yaeyama.linerchecker.ext.reference
import com.yaeyama.linerchecker.ext.valueEvents
import com.yaeyama_liner_checker.domain.top.Ports
import com.yaeyama_liner_checker.domain.top.TopPort
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class TopStatusRepositoryImpl : TopStatusRepository, KoinComponent {
    private val database: FirebaseDatabase by inject()

    override fun fetchTopStatuses(): Flow<List<Ports>> {
        val dbRef = database.reference("top_port")
        return dbRef.valueEvents.map { snapShot: DataSnapshot ->
            snapShot.getValue<TopPort>()?.toList() ?: listOf()
        }.catch {
            listOf<Ports>()
        }
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
