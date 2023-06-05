package com.ikemura.shared.repository

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.getValue
import com.yaeyama_liner_checker.domain.top.Ports
import com.yaeyama_liner_checker.domain.top.TopPort
import data.ext.reference
import data.ext.valueEvents
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class TopStatusRepositoryImpl : TopStatusRepository, KoinComponent {
    private val database: FirebaseDatabase by inject()

    override fun fetchTopStatuses(): Flow<UiState<List<Ports>>> {
        val dbRef = database.reference("top_port")
        return dbRef.valueEvents.map {
            val portList = it.getValue<TopPort>()?.toList() ?: listOf()
            UiState.Success(portList)
        }.catch { error ->
            UiState.Error(error)
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
