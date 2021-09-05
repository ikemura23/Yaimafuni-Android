package com.ikemura.shared.repository

import com.ikemura.shared.model.top.Ports
import com.ikemura.shared.model.top.TopPort
import dev.gitlive.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class TopStatusRepositoryImpl : TopStatusRepository, KoinComponent {
    private val database: FirebaseDatabase by inject()

    override fun fetchTopStatus(): Flow<UiState<TopPort>> {
        val dbRef = database.reference("top_port")
        return dbRef.valueEvents.map {
            val deserializeValue = it.value(TopPort.serializer())
            UiState.Success(deserializeValue)
        }.catch { error ->
            UiState.Error(error)
        }
    }

    override fun fetchTopStatuses(): Flow<UiState<List<Ports>>> {
        val dbRef = database.reference("top_port")
        return dbRef.valueEvents.map {
            val deserializeValue = it.value(TopPort.serializer())
            val portList: List<Ports> = deserializeValue.toList()
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