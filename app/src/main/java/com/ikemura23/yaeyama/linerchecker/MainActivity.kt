package com.ikemura23.yaeyama.linerchecker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ikemura23.yaeyama.linerchecker.ui.compose.YaimafuniApp
import com.ikemura23.yaeyama.linerchecker.ui.theme.YaimafuniAndroidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YaimafuniAndroidTheme {
                YaimafuniApp()
            }
        }
    }
}

