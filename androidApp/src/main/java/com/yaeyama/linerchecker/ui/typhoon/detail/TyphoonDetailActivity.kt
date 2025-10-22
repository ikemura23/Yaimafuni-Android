package com.yaeyama.linerchecker.ui.typhoon.detail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.yaeyama.linerchecker.ui.theme.YaimafuniAndroidTheme
import com.yaeyama.linerchecker.ui.typhoon.detail.compose.TyphoonDetailScreen

/**
 * 台風詳細Activity
 * DataBindingからComposeに移行したActivity
 */
class TyphoonDetailActivity : ComponentActivity() {

    companion object {
        const val EXTRA_TYPHOON = "extra_typhoon"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Intentからtyphoon情報を取得
        val typhoon = intent.getParcelableExtra<TyphoonDetailUiModel>(EXTRA_TYPHOON)

        setContent {
            YaimafuniAndroidTheme {
                TyphoonDetailScreen(
                    typhoon = typhoon,
                    onBackPressed = { finish() },
                )
            }
        }
    }
}
