package com.ikmr.banbara23.yaeyama_liner_checker.ui.dashboard

import android.content.Context
import android.util.AttributeSet
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.AbstractComposeView

/**
 * DashBoardをDataBindingで使うクラス
 * 全てComposeに置き換えたら削除する
 */
class DashBoardComposeView @JvmOverloads constructor(
    context: Context,
    attributes: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : AbstractComposeView(context, attributes, defStyleAttr) {

    @Composable
    override fun Content() {
    }
}