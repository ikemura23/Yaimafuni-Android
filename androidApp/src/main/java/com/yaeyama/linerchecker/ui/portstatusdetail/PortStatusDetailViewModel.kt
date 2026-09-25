package com.yaeyama.linerchecker.ui.portstatusdetail

import androidx.lifecycle.ViewModel
import com.yaeyama.linerchecker.R
import com.yaeyama.linerchecker.domain.repository.StatusDetailRepository
import com.yaeyama.linerchecker.domain.statusdetail.Company
import com.yaeyama.linerchecker.ui.common.asLoadState
import com.yaeyama.linerchecker.ui.common.reloadOnEach
import com.yaeyama.linerchecker.ui.common.stateInWhileSubscribed
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.update

/**
 * 運行詳細 ViewModel
 */
class PortStatusDetailViewModel(
    private val statusDetailRepository: StatusDetailRepository,
) : ViewModel() {

    /**
     * 購読する条件
     * @param attempt 再試行の回数。同じ会社・港でも値が変われば購読し直す
     */
    private data class Request(
        val company: Company,
        val portCode: String,
        val attempt: Int = 0,
    )

    private val request = MutableStateFlow<Request?>(null)

    /** 画面に表示する状態。画面が購読している間だけ取得する */
    val uiState: StateFlow<PortStatusDetailUiState> = request
        .filterNotNull()
        .reloadOnEach { (company, portCode) ->
            // 運航情報と時刻表は独立して取得し、片方の失敗がもう片方の表示を妨げないようにする
            combine(
                statusDetailRepository.fetchStatusDetail(company, portCode).asLoadState(
                    notFoundRes = R.string.port_status_not_found,
                    fetchFailedRes = R.string.port_status_fetch_failed,
                ),
                statusDetailRepository.fetchTimeTable(company, portCode).asLoadState(
                    notFoundRes = R.string.time_table_not_found,
                    fetchFailedRes = R.string.time_table_fetch_failed,
                ),
                ::PortStatusDetailUiState,
            )
        }
        .stateInWhileSubscribed(this, initialValue = PortStatusDetailUiState.InitialValue)

    /**
     * 運航詳細と時刻表の購読を開始する
     * 画面回転などで同じ条件で呼ばれた場合は購読し直さない
     */
    fun fetchDetail(
        company: Company,
        portCode: String,
    ) {
        request.update { current ->
            if (current?.company == company && current.portCode == portCode) current else Request(company, portCode)
        }
    }

    /**
     * 購読中の条件で再取得する
     */
    fun retry() {
        request.update { current -> current?.copy(attempt = current.attempt + 1) }
    }
}
