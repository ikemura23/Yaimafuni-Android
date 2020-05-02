package com.ikmr.banbara23.yaeyama_liner_checker.model

import androidx.annotation.DrawableRes
import com.ikmr.banbara23.yaeyama_liner_checker.R

/**
 * トップ画面で表示するステータスのタイプ
 */
enum class StatusType(
    val text: String,
    @DrawableRes val background: Int
) {
    NORMAL("通常運行", R.drawable.shape_rounded_corners_status_nomal),
    CANCEL("欠航あり", R.drawable.shape_rounded_corners_status_cancel),
    CATION("注意あり", R.drawable.shape_rounded_corners_status_cation),
    SUSPEND("運休あり", R.drawable.shape_rounded_corners_status_cation),
}
