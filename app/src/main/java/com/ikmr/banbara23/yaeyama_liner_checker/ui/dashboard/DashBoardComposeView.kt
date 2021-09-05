package com.ikmr.banbara23.yaeyama_liner_checker.ui.dashboard

import android.content.Context
import android.util.AttributeSet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.AbstractComposeView
import com.ikemura.shared.model.top.Ports

/**
 * DashBoardをDataBindingで使うクラス
 * 全てComposeに置き換えたら削除する
 */
class DashBoardComposeView @JvmOverloads constructor(
    context: Context,
    attributes: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : AbstractComposeView(context, attributes, defStyleAttr) {

    private val _ports = mutableStateOf<List<Ports>>(listOf())
    var ports: List<Ports>
        get() = _ports.value
        set(value) {
            _ports.value = value
        }
    var onRowClick: (Ports) -> Unit = {}

    @Composable
    override fun Content() {
        DashBoard(_ports.value, onRowClick)
    }
}