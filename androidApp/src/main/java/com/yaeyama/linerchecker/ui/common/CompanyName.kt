package com.yaeyama.linerchecker.ui.common

import androidx.annotation.StringRes
import com.yaeyama.linerchecker.R
import com.yaeyama.linerchecker.domain.statusdetail.Company

/** 画面に表示する会社名（正式名称） */
@get:StringRes
val Company.nameRes: Int
    get() = when (this) {
        Company.ANEI -> R.string.company_anei
        Company.YKF -> R.string.company_ykf
    }
