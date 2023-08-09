package com.yaeyama.linerchecker.ui.portstatusdetail

import android.content.Context
import android.util.AttributeSet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.AbstractComposeView
import com.yaeyama.linerchecker.ui.portstatusdetail.component.PortMainStatus
import com.yaeyama_liner_checker.domain.statusdetail.PortStatus

/**
 * xmlでComposeを表示するためのViewクラス
 *
 * 参考にしたサイト
 * https://developer.android.google.cn/jetpack/compose/interop/compose-in-existing-arch?hl=ja
 * https://proandroiddev.com/jetpack-compose-interop-part-2-using-compose-in-traditional-android-views-and-layouts-with-a3c50fc2eaa5
 */
class PortMainStatusComposeView @JvmOverloads constructor(
    context: Context,
    attributes: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : AbstractComposeView(context, attributes, defStyleAttr) {

    private val portStatusState = mutableStateOf<PortStatus>(PortStatus())

    var portStatus
        get() = portStatusState.value
        set(value) {
            portStatusState.value = value
        }

    @Composable
    override fun Content() {
        PortMainStatus(
            portName = portStatusState.value.portName,
            status = portStatusState.value.status,
            statusDescription = portStatusState.value.comment,
        )
    }
}
