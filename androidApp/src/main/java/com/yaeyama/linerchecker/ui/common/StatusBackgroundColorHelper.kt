package com.yaeyama.linerchecker.ui.common

import com.yaeyama.linerchecker.domain.statusdetail.Status
import com.yaeyama.linerchecker.ui.theme.StatusColor

/**
 * ステータスの値を判定して背景色を返す
 */
fun Status.getStatusBackgroundColor() = when (this.code) {
    "nomal", "normal" -> StatusColor.Normal
    "cation" -> StatusColor.Cation
    "cancel" -> StatusColor.Cancel
    else -> StatusColor.Cation
}
