package com.yaeyama.linerchecker.ui.dashboard

import androidx.annotation.StringRes
import com.yaeyama.linerchecker.domain.top.Ports

data class DashBoardUiState(
    val isLoading: Boolean,
    /** 取得に失敗したときに表示するメッセージ。成功時はnull */
    @StringRes val errorMessageRes: Int?,
    val portList: List<Ports>,
) {
    val isError: Boolean get() = errorMessageRes != null

    companion object {
        val InitialValue = DashBoardUiState(
            isLoading = false,
            errorMessageRes = null,
            portList = listOf(),
        )
    }
}
