package com.yaeyama.linerchecker.ui.dashboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yaeyama.linerchecker.core.Event
import com.yaeyama.linerchecker.core.toEvent
import com.yaeyama.linerchecker.repository.TopStatusRepositoryImpl
import com.yaeyama_liner_checker.domain.top.Ports
import com.yaeyama_liner_checker.domain.top.TopPort
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import timber.log.Timber

/**
 * トップに表示するステータスのダッシュボード ViewModel
 */
class DashBoardViewModelImpl : DashBoardViewModel() {
    private val topStatusRepository = TopStatusRepositoryImpl() // TODO: DIする
    override val nav = MutableLiveData<Event<Nav>>()
    private val isLoading = MutableStateFlow(false)
    private val isError = MutableStateFlow(false)
    private val portList = topStatusRepository.fetchTopStatuses()

    override val uiState: StateFlow<DashBoardUiState> = combine(
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

    // override fun fetchTopStatuses(): Flow<UiState<List<Ports>>> = topStatusRepository.fetchTopStatuses()

    /**
     * 港クリック
     */
    override fun onClickPort(ports: Ports?) {
        Timber.d("clicked: $ports")
        ports ?: return
        nav.value = Nav.GoDetail(ports).toEvent()
    }

    sealed class Nav {
        data class GoDetail(val ports: Ports) : Nav()
    }
}

sealed class TopPortStatusState {
    data class Success(val topPort: TopPort) : TopPortStatusState()
    data class Error(val error: Throwable) : TopPortStatusState()
}
