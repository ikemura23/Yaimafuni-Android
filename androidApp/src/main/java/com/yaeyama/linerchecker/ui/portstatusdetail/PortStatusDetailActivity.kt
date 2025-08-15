package com.yaeyama.linerchecker.ui.portstatusdetail

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.yaeyama.linerchecker.ui.portstatusdetail.compose.PortStatusDetailScreen
import com.yaeyama.linerchecker.ui.theme.YaimafuniAndroidTheme
import org.koin.android.ext.android.inject

/**
 * ステータス詳細のActivity - Compose移行完了版
 * DataBindingとFragment/ViewPager2からCompose完全移行
 */
class PortStatusDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_PORT_CODE = "port_code"
        const val EXTRA_PORT_NAME = "port_name"
    }

    private val viewModel: PortStatusDetailViewModel by inject()

    private val portCode: String by lazy {
        intent.getStringExtra(EXTRA_PORT_CODE) ?: ""
    }

    private val portName: String by lazy {
        intent.getStringExtra(EXTRA_PORT_NAME) ?: ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            YaimafuniAndroidTheme {
                PortStatusDetailScreen(
                    portCode = portCode,
                    portName = portName,
                    viewModel = viewModel,
                    onBackPressed = { finish() }
                )
            }
        }
    }
}
