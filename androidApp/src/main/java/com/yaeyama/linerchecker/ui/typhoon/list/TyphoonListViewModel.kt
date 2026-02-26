package com.yaeyama.linerchecker.ui.typhoon.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yaeyama_liner_checker.domain.repository.TyphoonRepository
import com.yaeyama_liner_checker.domain.tyhoon.Typhoon
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

/**
 * 台風一覧 ViewModel
 */
class TyphoonListViewModel(
    private val typhoonRepository: TyphoonRepository,
) : ViewModel() {

    /**
     * 台風一覧を取得する
     */
    fun getTyphoonList(): Flow<List<Typhoon>> = typhoonRepository.fetchTyphoonList()

    val uiState: StateFlow<TyphoonUiState> = typhoonRepository.fetchTyphoonList()
        .map<List<Typhoon>, TyphoonUiState> { TyphoonUiState.Data(it) }
        .onStart { emit(TyphoonUiState.Loading) }
        .catch { emit(TyphoonUiState.Error) }
        .stateIn(
            scope = viewModelScope,
            started = WhileSubscribed(5000), // ５秒間購読がなければコルーチンを停止する
            initialValue = TyphoonUiState.Loading,
        )
}

sealed interface TyphoonUiState {
    data class Data(val typhoons: List<Typhoon>) : TyphoonUiState
    object Loading : TyphoonUiState
    object Error : TyphoonUiState
}
