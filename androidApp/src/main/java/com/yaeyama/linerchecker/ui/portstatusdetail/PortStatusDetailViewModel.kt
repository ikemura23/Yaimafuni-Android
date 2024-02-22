package com.yaeyama.linerchecker.ui.portstatusdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yaeyama.linerchecker.repository.StatusDetailRepository
import com.yaeyama_liner_checker.domain.statusdetail.Company
import com.yaeyama_liner_checker.domain.statusdetail.PortStatus
import com.yaeyama_liner_checker.domain.time_table.TimeTable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * 運行詳細 ViewModel
 */
class PortStatusDetailViewModel(
    private val statusDetailRepository: StatusDetailRepository,
) : ViewModel() {

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

    fun fetchDetail(
        company: Company,
        portCode: String,
    ) {
        viewModelScope.launch {
            runCatching {
                val res: Flow<PortStatus> = statusDetailRepository.fetchStatusDetail(company = company, portCode = portCode)
                portStatus.update { res.first() } // TODO: firstをやめたい
            }.onFailure {
                isError.update { true }
            }

        }
        viewModelScope.launch {
            runCatching {
                timeTable.update {
                    statusDetailRepository.fetchTimeTable(company, portCode).first()  // TODO: firstをやめたい
                }
            }.onFailure {
                isError.update { true } // TODO: TimeTableだけエラーの場合を考慮する
            }
        }
    }
}
