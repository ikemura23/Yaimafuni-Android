package com.ikmr.banbara23.yaeyama_liner_checker.ui.typhoon.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yaeyama_liner_checker.domain.tyhoon.Typhoon
import com.ikemura.shared.repository.TyphoonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * 台風一覧 ViewModel
 */
class TyphoonListViewModel : ViewModel(), KoinComponent {
    private val typhoonRepository: TyphoonRepository by inject()

    /**
     * 台風一覧を取得する
     */
    fun getTyphoonList(): Flow<List<Typhoon>> = typhoonRepository.fetchTyphoonList()

    private val typhoons: Flow<List<Typhoon>> = typhoonRepository.fetchTyphoonList()
    val uiState: StateFlow<TyphoonUiState> = flow {
        typhoonRepository.fetchTyphoonList().map {
            TyphoonUiState.Data(it)
        }
            .onStart { emit(TyphoonUiState.Loading) }
            .catch { emit(TyphoonUiState.Error) }
    }.stateIn(
        scope = viewModelScope,
        started = WhileSubscribed(5000), // ５秒間購読がなければコルーチンを停止する
        initialValue = TyphoonUiState.Loading
    )
}

sealed interface TyphoonUiState {
    data class Data(val typhoons: List<Typhoon>) : TyphoonUiState
    object Loading : TyphoonUiState
    object Error : TyphoonUiState
}
