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

    private val viewModel: PortStatusDetailViewModel by inject()

    private val portCode: String by lazy {
        PortStatusDetailActivityArgs.fromBundle(intent.extras!!).portCode
    }

    private val portName: String by lazy {
        PortStatusDetailActivityArgs.fromBundle(intent.extras!!).portName
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
