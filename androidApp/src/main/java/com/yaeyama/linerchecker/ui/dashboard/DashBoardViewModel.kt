package com.yaeyama.linerchecker.ui.dashboard

import androidx.lifecycle.ViewModel
import com.yaeyama.linerchecker.R
import com.yaeyama.linerchecker.domain.top.Ports
import com.yaeyama.linerchecker.domain.usecase.GetTopStatuses
import com.yaeyama.linerchecker.ui.common.LoadState
import com.yaeyama.linerchecker.ui.common.asLoadState
import com.yaeyama.linerchecker.ui.common.reloadOnEach
import com.yaeyama.linerchecker.ui.common.stateInWhileSubscribed
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart

/**
 * トップに表示するステータスのダッシュボード ViewModel
 */
class DashBoardViewModel(
    private val getTopStatuses: GetTopStatuses,
) : ViewModel() {

    private val retryTrigger = MutableSharedFlow<Unit>(extraBufferCapacity = 1)

    /** 画面に表示する読み込み状態。画面が購読している間だけ取得する */
    val uiState: StateFlow<LoadState<List<Ports>>> = retryTrigger
        .onStart { emit(Unit) }
        .reloadOnEach {
            getTopStatuses().asLoadState(
                notFoundRes = R.string.dashboard_not_found,
                fetchFailedRes = R.string.dashboard_fetch_failed,
            )
        }
        .stateInWhileSubscribed(this, initialValue = LoadState.Loading)

    /** 取得をやり直す */
    fun retry() {
        retryTrigger.tryEmit(Unit)
    }
}
