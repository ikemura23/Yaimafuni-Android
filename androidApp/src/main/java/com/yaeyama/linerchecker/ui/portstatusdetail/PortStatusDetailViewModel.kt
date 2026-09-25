package com.yaeyama.linerchecker.ui.portstatusdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yaeyama.linerchecker.R
import com.yaeyama.linerchecker.domain.repository.StatusDetailRepository
import com.yaeyama.linerchecker.domain.statusdetail.Company
import com.yaeyama.linerchecker.domain.statusdetail.PortStatus
import com.yaeyama.linerchecker.domain.timetable.TimeTable
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
 * 運行詳細 ViewModel
 */
class PortStatusDetailViewModel(
    private val statusDetailRepository: StatusDetailRepository,
) : ViewModel() {

    private val isLoading = MutableStateFlow(false)
    private val errorMessageRes = MutableStateFlow<Int?>(null)
    private val timeTableErrorMessageRes = MutableStateFlow<Int?>(null)
    private val portStatus = MutableStateFlow(PortStatus())
    private val timeTable = MutableStateFlow(TimeTable())

    private var statusDetailJob: Job? = null
    private var timeTableJob: Job? = null

    /** 購読中の会社と港。同じ条件での重複した購読を防ぐために保持する */
    private var currentRequest: Pair<Company, String>? = null

    val uiState: StateFlow<PortStatusDetailUiState> = combine(
        isLoading,
        errorMessageRes,
        timeTableErrorMessageRes,
        portStatus,
        timeTable,
    ) {
            isLoading,
            errorMessageRes,
            timeTableErrorMessageRes,
            portStatus,
            timeTable,
        ->
        PortStatusDetailUiState(
            isLoading,
            errorMessageRes,
            timeTableErrorMessageRes,
            portStatus,
            timeTable,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = PortStatusDetailUiState.InitialValue,
    )

    /**
     * 運航詳細と時刻表の購読を開始する
     * 画面回転などで同じ条件で呼ばれた場合、購読中でエラーが無ければ何もしない
     */
    fun fetchDetail(
        company: Company,
        portCode: String,
    ) {
        val request = company to portCode
        val state = uiState.value
        if (request == currentRequest && !state.isError && !state.isTimeTableError) return
        subscribe(request)
    }

    /**
     * 購読中の条件で再取得する
     */
    fun retry() {
        currentRequest?.let { subscribe(it) }
    }

    private fun subscribe(request: Pair<Company, String>) {
        val (company, portCode) = request
        currentRequest = request
        statusDetailJob?.cancel()
        timeTableJob?.cancel()
        errorMessageRes.update { null }
        timeTableErrorMessageRes.update { null }

        statusDetailJob = viewModelScope.launch {
            statusDetailRepository.fetchStatusDetail(company = company, portCode = portCode)
                .onStart { isLoading.update { true } }
                .onEach { isLoading.update { false } }
                .catch { e ->
                    Timber.e(e, "fetchStatusDetail failed")
                    isLoading.update { false }
                    errorMessageRes.update {
                        e.toErrorMessageRes(
                            notFoundRes = R.string.port_status_not_found,
                            fetchFailedRes = R.string.port_status_fetch_failed,
                        )
                    }
                }
                .collect { portStatus.value = it }
        }

        timeTableJob = viewModelScope.launch {
            statusDetailRepository.fetchTimeTable(company, portCode)
                .catch { e ->
                    Timber.e(e, "fetchTimeTable failed")
                    timeTableErrorMessageRes.update {
                        e.toErrorMessageRes(
                            notFoundRes = R.string.time_table_not_found,
                            fetchFailedRes = R.string.time_table_fetch_failed,
                        )
                    }
                }
                .collect { timeTable.value = it }
        }
    }
}
