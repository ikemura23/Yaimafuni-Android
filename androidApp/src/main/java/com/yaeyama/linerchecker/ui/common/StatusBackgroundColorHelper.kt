package com.yaeyama.linerchecker.ui.common

import com.yaeyama.linerchecker.domain.statusdetail.OperationStatus
import com.yaeyama.linerchecker.domain.statusdetail.Status
import com.yaeyama.linerchecker.domain.statusdetail.toOperationStatus
import com.yaeyama.linerchecker.ui.theme.StatusColor

/**
 * 運航状況に対応する背景色を返す
 */
fun Status.getStatusBackgroundColor() = when (toOperationStatus()) {
    OperationStatus.NORMAL -> StatusColor.Normal
    OperationStatus.CANCEL -> StatusColor.Cancel
    OperationStatus.CAUTION, null -> StatusColor.Caution
}
