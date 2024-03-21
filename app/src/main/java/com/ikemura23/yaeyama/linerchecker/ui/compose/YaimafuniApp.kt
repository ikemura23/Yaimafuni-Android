package com.ikemura23.yaeyama.linerchecker.ui.compose

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.ikemura23.yaeyama.linerchecker.ui.compose.navigation.YaimafuniNavHost

@Composable
fun YaimafuniApp() {
    val navController = rememberNavController()
    YaimafuniNavHost(
        navController = navController
    )
}
