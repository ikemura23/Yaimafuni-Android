package com.yaeyama.linerchecker.ui.typhoon.list

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yaeyama.linerchecker.R
import com.yaeyama.linerchecker.domain.repository.TyphoonRepository
import com.yaeyama.linerchecker.domain.typhoon.Typhoon
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import timber.log.Timber

/**
 * 台風一覧 ViewModel
 */
@OptIn(ExperimentalCoroutinesApi::class)
class TyphoonListViewModel(
    private val typhoonRepository: TyphoonRepository,
) : ViewModel() {

    private val retryTrigger = MutableSharedFlow<Unit>(extraBufferCapacity = 1)

    val uiState: StateFlow<TyphoonUiState> = retryTrigger
        .onStart { emit(Unit) }
        .flatMapLatest {
            typhoonRepository.fetchTyphoonList()
                .map<List<Typhoon>, TyphoonUiState> { TyphoonUiState.Data(it) }
                .onStart { emit(TyphoonUiState.Loading) }
                .catch {
                    Timber.e(it, "fetchTyphoonList failed")
                    // 台風が無いときは空リストになるため、エラーは取得失敗のみ
                    emit(TyphoonUiState.Error(R.string.typhoon_list_fetch_failed))
                }
        }
        .stateIn(
            scope = viewModelScope,
            started = WhileSubscribed(5000), // ５秒間購読がなければコルーチンを停止する
            initialValue = TyphoonUiState.Loading,
        )

    fun retry() {
        retryTrigger.tryEmit(Unit)
    }
}

sealed interface TyphoonUiState {
    data class Data(val typhoons: List<Typhoon>) : TyphoonUiState
    object Loading : TyphoonUiState
    data class Error(@StringRes val messageRes: Int) : TyphoonUiState
}
