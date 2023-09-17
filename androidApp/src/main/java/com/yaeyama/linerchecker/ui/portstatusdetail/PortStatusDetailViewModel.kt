package com.yaeyama.linerchecker.ui.portstatusdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yaeyama.linerchecker.repository.StatusDetailRepository
import com.yaeyama.linerchecker.usecase.GetStatusDetail
import com.yaeyama_liner_checker.domain.statusdetail.PortStatus
import com.yaeyama_liner_checker.domain.time_table.TimeTable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

/**
 * 運行詳細 ViewModel
 */
class PortStatusDetailViewModel : ViewModel() {
    // UseCaseでFlowを受け取る
    val getStatusDetail = GetStatusDetail(StatusDetailRepository())

    private val isLoading = MutableStateFlow(false)
    private val isError = MutableStateFlow(false)
    private val portStatus = MutableStateFlow(PortStatus())
    private val timeTable = MutableStateFlow(TimeTable())

    val uiState: StateFlow<PortStatusDetailUiState> = combine(
        isLoading,
        isError,
        portStatus,
        timeTable,
    ) {
            isLoading,
            isError,
            portStatus,
            timeTable,
        ->
        PortStatusDetailUiState(
            isLoading,
            isError,
            portStatus,
            timeTable,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = PortStatusDetailUiState.InitialValue,
    )
}
