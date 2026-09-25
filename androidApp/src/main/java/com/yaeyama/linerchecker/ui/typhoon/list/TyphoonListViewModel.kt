package com.yaeyama.linerchecker.ui.typhoon.list

import androidx.lifecycle.ViewModel
import com.yaeyama.linerchecker.R
import com.yaeyama.linerchecker.domain.typhoon.Typhoon
import com.yaeyama.linerchecker.domain.usecase.GetTyphoonList
import com.yaeyama.linerchecker.ui.common.LoadState
import com.yaeyama.linerchecker.ui.common.asLoadState
import com.yaeyama.linerchecker.ui.common.reloadOnEach
import com.yaeyama.linerchecker.ui.common.stateInWhileSubscribed
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart

/**
 * 台風一覧 ViewModel
 */
class TyphoonListViewModel(
    private val getTyphoonList: GetTyphoonList,
) : ViewModel() {

    private val retryTrigger = MutableSharedFlow<Unit>(extraBufferCapacity = 1)

    /** 画面に表示する読み込み状態。画面が購読している間だけ取得する */
    val uiState: StateFlow<LoadState<List<Typhoon>>> = retryTrigger
        .onStart { emit(Unit) }
        .reloadOnEach {
            // 台風が無いときは空リストになるため、エラーは取得失敗のみ
            getTyphoonList().asLoadState(
                notFoundRes = R.string.typhoon_list_fetch_failed,
                fetchFailedRes = R.string.typhoon_list_fetch_failed,
            )
        }
        .stateInWhileSubscribed(this, initialValue = LoadState.Loading)

    /** 取得をやり直す */
    fun retry() {
        retryTrigger.tryEmit(Unit)
    }
}
