package com.ikmr.banbara23.yaeyama_liner_checker.ui.portstatusdetail

import android.content.Context
import android.util.AttributeSet
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.AbstractComposeView

/**
 * 時刻別の運行リストをxmlでComposeを表示するComposeView
 *
 * 参考にしたサイト
 * https://developer.android.google.cn/jetpack/compose/interop/compose-in-existing-arch?hl=ja
 * https://proandroiddev.com/jetpack-compose-interop-part-2-using-compose-in-traditional-android-views-and-layouts-with-a3c50fc2eaa5
 *
 * TODO: 完全にComposeに移行したらこのクラスは削除する
 */
class TimeTableListComposeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : AbstractComposeView(context, attrs, defStyleAttr) {

    @Composable
    override fun Content() {
        val items = listOf("a", "b", "c", "d", "f")
        TimeTableList(items = items)
    }
}