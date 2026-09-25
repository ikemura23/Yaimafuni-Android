package com.yaeyama.linerchecker.ui.portstatusdetail

import androidx.annotation.StringRes
import com.yaeyama.linerchecker.domain.statusdetail.PortStatus
import com.yaeyama.linerchecker.domain.timetable.TimeTable

data class PortStatusDetailUiState(
    val isLoading: Boolean,
    /** 運航情報の取得に失敗したときに表示するメッセージ。成功時はnull */
    @StringRes val errorMessageRes: Int?,
    /** 時刻表の取得に失敗したときに表示するメッセージ。成功時はnull */
    @StringRes val timeTableErrorMessageRes: Int?,
    val portStatus: PortStatus,
    val timeTable: TimeTable,
) {
    val isError: Boolean get() = errorMessageRes != null
    val isTimeTableError: Boolean get() = timeTableErrorMessageRes != null

    companion object {
        val InitialValue = PortStatusDetailUiState(
            isLoading = false,
            errorMessageRes = null,
            timeTableErrorMessageRes = null,
            portStatus = PortStatus(),
            timeTable = TimeTable(),
        )
    }
}
