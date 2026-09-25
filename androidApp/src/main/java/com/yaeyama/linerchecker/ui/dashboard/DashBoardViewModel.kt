package com.yaeyama.linerchecker.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yaeyama.linerchecker.R
import com.yaeyama.linerchecker.domain.top.Ports
import com.yaeyama.linerchecker.domain.usecase.GetTopStatuses
import com.yaeyama.linerchecker.ui.common.toErrorMessageRes
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
    private val getTopStatuses: GetTopStatuses,
) : ViewModel() {

    private val isLoading = MutableStateFlow(false)
    private val errorMessageRes = MutableStateFlow<Int?>(null)
    private val portList: MutableStateFlow<List<Ports>> = MutableStateFlow(listOf())

    private var topStatusesJob: Job? = null

    val uiState: StateFlow<DashBoardUiState> = combine(
        isLoading,
        errorMessageRes,
        portList,
    ) { loading, errorMessage, ports ->
        DashBoardUiState(
            isLoading = loading,
            errorMessageRes = errorMessage,
            portList = ports,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = DashBoardUiState.InitialValue,
    )

    init {
        fetchPortList()
    }

    /**
     * 運航状況の購読を（再）開始する
     * 初回は ViewModel 生成時に呼ばれるため、画面表示のたびに呼ぶ必要はない
     */
    fun fetchPortList() {
        topStatusesJob?.cancel()
        topStatusesJob = viewModelScope.launch {
            errorMessageRes.update { null }
            getTopStatuses()
                .onStart { isLoading.update { true } }
                .onEach { isLoading.update { false } }
                .catch { e ->
                    Timber.e(e, "fetchTopStatuses failed")
                    isLoading.update { false }
                    errorMessageRes.update {
                        e.toErrorMessageRes(
                            notFoundRes = R.string.dashboard_not_found,
                            fetchFailedRes = R.string.dashboard_fetch_failed,
                        )
                    }
                }
                .collect { portList.value = it }
        }
    }
}
