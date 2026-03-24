package com.yaeyama.linerchecker.ui.portstatusdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yaeyama_liner_checker.domain.repository.StatusDetailRepository
import com.yaeyama_liner_checker.domain.statusdetail.Company
import com.yaeyama_liner_checker.domain.statusdetail.PortStatus
import com.yaeyama_liner_checker.domain.time_table.TimeTable
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

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

    private var statusDetailJob: Job? = null
    private var timeTableJob: Job? = null

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
        statusDetailJob?.cancel()
        timeTableJob?.cancel()
        isError.update { false }

        statusDetailJob = viewModelScope.launch {
            statusDetailRepository.fetchStatusDetail(company = company, portCode = portCode)
                .catch { e ->
                    Timber.e(e, "fetchStatusDetail failed")
                    isError.update { true }
                }
                .collect { portStatus.value = it }
        }

        timeTableJob = viewModelScope.launch {
            statusDetailRepository.fetchTimeTable(company, portCode)
                .catch { e ->
                    Timber.e(e, "fetchTimeTable failed")
                    // TODO: 時刻表のみ失敗した場合は isError を分離して扱う
                    isError.update { true }
                }
                .collect { timeTable.value = it }
        }
    }
}
