package com.yaeyama.linerchecker.ui.portstatusdetail

import androidx.annotation.StringRes
import com.yaeyama.linerchecker.domain.statusdetail.PortStatus
import com.yaeyama.linerchecker.domain.timetable.TimeTable
import com.yaeyama.linerchecker.ui.common.LoadState

/**
 * 運航詳細画面の状態
 * 運航情報と時刻表はそれぞれ独立して読み込む
 */
data class PortStatusDetailUiState(
    val portStatusState: LoadState<PortStatus>,
    val timeTableState: LoadState<TimeTable>,
) {
    val isLoading: Boolean get() = portStatusState is LoadState.Loading

    /** 運航情報の取得に失敗したときに表示するメッセージ。成功時はnull */
    @get:StringRes
    val errorMessageRes: Int? get() = (portStatusState as? LoadState.Error)?.messageRes

    /** 時刻表の取得に失敗したときに表示するメッセージ。成功時はnull */
    @get:StringRes
    val timeTableErrorMessageRes: Int? get() = (timeTableState as? LoadState.Error)?.messageRes

    val isError: Boolean get() = errorMessageRes != null
    val isTimeTableError: Boolean get() = timeTableErrorMessageRes != null

    val portStatus: PortStatus get() = (portStatusState as? LoadState.Success)?.data ?: PortStatus()
    val timeTable: TimeTable get() = (timeTableState as? LoadState.Success)?.data ?: TimeTable()

    companion object {
        val InitialValue = PortStatusDetailUiState(
            portStatusState = LoadState.Loading,
            timeTableState = LoadState.Loading,
        )
    }
}
