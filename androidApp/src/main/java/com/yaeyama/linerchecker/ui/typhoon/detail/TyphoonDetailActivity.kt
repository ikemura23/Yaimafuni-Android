package com.yaeyama.linerchecker.ui.typhoon.detail

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
        // アプリはダークモード非対応のため、システムのダークモード設定に関わらず常にlightスタイル(濃色アイコン)にする
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT),
        )

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
