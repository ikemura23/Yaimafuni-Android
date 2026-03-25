package com.yaeyama.linerchecker.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yaeyama_liner_checker.domain.repository.TopStatusRepository
import com.yaeyama_liner_checker.domain.top.Ports
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * トップに表示するステータスのダッシュボード ViewModel
 */
class DashBoardViewModel(
    private val topStatusRepository: TopStatusRepository,
) : ViewModel() {

    private val isLoading = MutableStateFlow(false)
    private val isError = MutableStateFlow(false)
    private val portList: MutableStateFlow<List<Ports>> = MutableStateFlow(listOf())

    private var topStatusesJob: Job? = null

    val uiState: StateFlow<DashBoardUiState> = combine(
        isLoading,
        isError,
        portList,
    ) { loading, error, ports ->
        DashBoardUiState(
            isLoading = loading,
            isError = error,
            portList = ports,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = DashBoardUiState.InitialValue,
    )

    fun fetchPortList() {
        topStatusesJob?.cancel()
        topStatusesJob = viewModelScope.launch {
            isError.update { false }
            topStatusRepository.fetchTopStatuses()
                .onStart { isLoading.update { true } }
                .onEach { isLoading.update { false } }
                .catch { e ->
                    Timber.e(e, "fetchTopStatuses failed")
                    isLoading.update { false }
                    isError.update { true }
                }
                .collect { portList.value = it }
        }
    }
}
