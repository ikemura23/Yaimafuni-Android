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
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
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
    private val isTimeTableError = MutableStateFlow(false)
    private val portStatus = MutableStateFlow(PortStatus())
    private val timeTable = MutableStateFlow(TimeTable())

    private var statusDetailJob: Job? = null
    private var timeTableJob: Job? = null

    val uiState: StateFlow<PortStatusDetailUiState> = combine(
        isLoading,
        isError,
        isTimeTableError,
        portStatus,
        timeTable,
    ) {
            isLoading,
            isError,
            isTimeTableError,
            portStatus,
            timeTable,
        ->
        PortStatusDetailUiState(
            isLoading,
            isError,
            isTimeTableError,
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
        isTimeTableError.update { false }

        statusDetailJob = viewModelScope.launch {
            statusDetailRepository.fetchStatusDetail(company = company, portCode = portCode)
                .onStart { isLoading.update { true } }
                .onEach { isLoading.update { false } }
                .catch { e ->
                    Timber.e(e, "fetchStatusDetail failed")
                    isLoading.update { false }
                    isError.update { true }
                }
                .collect { portStatus.value = it }
        }

        timeTableJob = viewModelScope.launch {
            statusDetailRepository.fetchTimeTable(company, portCode)
                .catch { e ->
                    Timber.e(e, "fetchTimeTable failed")
                    isTimeTableError.update { true }
                }
                .collect { timeTable.value = it }
        }
    }
}
