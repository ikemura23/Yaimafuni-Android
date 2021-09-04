package com.ikmr.banbara23.yaeyama_liner_checker.ui.dashboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.ikemura.shared.model.top.Ports
import com.ikemura.shared.model.top.TopPort
import com.ikemura.shared.repository.TopStatusRepository
import com.ikemura.shared.repository.UiState
import com.ikmr.banbara23.yaeyama_liner_checker.core.Event
import com.ikmr.banbara23.yaeyama_liner_checker.core.toEvent
import com.ikmr.banbara23.yaeyama_liner_checker.repository.TopPortStatusRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * トップに表示するステータスのダッシュボード ViewModel
 */
class DashBoardViewModelImpl : DashBoardViewModel() {

    override val uiState = MutableLiveData<TopPort>()
    override val nav = MutableLiveData<Event<Nav>>()

    private val database: DatabaseReference by lazy {
        FirebaseDatabase.getInstance().reference.ref.child("top_port")
    }

    private val topPortStatusRepository: TopPortStatusRepository by lazy {
        TopPortStatusRepository(database)
    }

    private val topStatusRepository = TopStatusRepository()

    fun fetchTopPortStatus() {
        viewModelScope.launch {
            topPortStatusRepository.getTopPortStatus().collect { state ->
                when (state) {
                    is TopPortStatusState.Success -> uiState.value = state.topPort
                    else -> Timber.e("$state")
                }
            }
        }
    }

    fun fetchTopStatus(): Flow<UiState<TopPort>> {
        return topStatusRepository.fetchTopStatus()
    }

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
