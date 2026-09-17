package com.yaeyama.linerchecker.ui.portstatusdetail

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.yaeyama.linerchecker.ui.portstatusdetail.compose.PortStatusDetailScreen
import com.yaeyama.linerchecker.ui.theme.YaimafuniAndroidTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * ステータス詳細のActivity - Compose移行完了版
 * DataBindingとFragment/ViewPager2からCompose完全移行
 */
class PortStatusDetailActivity : ComponentActivity() {

    companion object {
        const val EXTRA_PORT_CODE = "port_code"
        const val EXTRA_PORT_NAME = "port_name"
    }

    private val viewModel: PortStatusDetailViewModel by viewModel()

    private val portCode: String by lazy {
        intent.getStringExtra(EXTRA_PORT_CODE) ?: ""
    }

    private val portName: String by lazy {
        intent.getStringExtra(EXTRA_PORT_NAME) ?: ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // アプリはダークモード非対応のため、システムのダークモード設定に関わらず常にlightスタイル(濃色アイコン)にする
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT),
        )

        setContent {
            YaimafuniAndroidTheme {
                PortStatusDetailScreen(
                    portCode = portCode,
                    portName = portName,
                    viewModel = viewModel,
                    onBackPressed = { finish() },
                )
            }
        }
    }
}
