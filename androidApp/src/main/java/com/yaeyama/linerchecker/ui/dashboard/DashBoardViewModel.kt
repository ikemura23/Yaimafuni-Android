package com.yaeyama.linerchecker.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yaeyama.linerchecker.repository.TopStatusRepositoryImpl
import com.yaeyama_liner_checker.domain.top.Ports
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * トップに表示するステータスのダッシュボード ViewModel
 */
class DashBoardViewModel : ViewModel() {
    private val topStatusRepository = TopStatusRepositoryImpl() // TODO: DIする
    private val isLoading = MutableStateFlow(false)
    private val isError = MutableStateFlow(false)
    private val portList: MutableStateFlow<List<Ports>> = MutableStateFlow(listOf())

    val uiState: StateFlow<DashBoardUiState> = combine(
        isLoading,
        isError,
        portList,
    ) { isError, isLoading, portList ->
        DashBoardUiState(
            isLoading = isLoading,
            isError = isError,
            portList = portList,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = DashBoardUiState.InitialValue,
    )

    suspend fun fetchPortList() {
        viewModelScope.launch {
            portList.update {
                topStatusRepository.fetchTopStatuses().first()
            }
        }
    }
}
