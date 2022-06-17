package com.ikmr.banbara23.yaeyama_liner_checker.ui.portstatusdetail

import android.content.Context
import android.util.AttributeSet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.AbstractComposeView
import com.yaeyama_liner_checker.domain.statusdetail.PortStatus
import com.yaeyama_liner_checker.domain.statusdetail.Status

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

    private val portNameState = mutableStateOf("")
    private val statusState = mutableStateOf<Status>(Status())
    private val statusDescriptionState = mutableStateOf("")
    private val portStatusState = mutableStateOf<PortStatus>(PortStatus())

    var portName: String
        get() = portNameState.value
        set(value) {
            portNameState.value = value
        }

    var status
        get() = statusState.value
        set(value) {
            statusState.value = value
        }

    var statusDescription
        get() = statusDescriptionState.value
        set(value) {
            statusDescriptionState.value = value
        }

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
            statusDescription = portStatusState.value.comment
        )
    }
}
