package com.ikmr.banbara23.yaeyama_liner_checker.ui

import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.ikmr.banbara23.yaeyama_liner_checker.R

/**
 * 運行ステータスコードから背景（角丸background）をセットする
 */
@BindingAdapter("backgroundColorOfStatus")
fun setBackgroundColorFromStatus(view: TextView, status: String?) {
    if (status.isNullOrEmpty()) return

    val drawable = when (status) {
        "normal" -> R.drawable.shape_rounded_corners_status_nomal // 運行
        "cancel" -> R.drawable.shape_rounded_corners_status_cancel // 欠航
        "suspend" -> R.drawable.shape_rounded_corners_status_cation // 未定 or 運休
        else -> R.drawable.shape_rounded_corners_status_cation
    }
    view.background = ContextCompat.getDrawable(view.context, drawable)
}

