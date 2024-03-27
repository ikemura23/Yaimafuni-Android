package com.ikemura23.yaeyama.linerchecker.ui.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.ikemura23.yaeyama.linerchecker.ui.compose.navigation.MainNavigation
import com.ikemura23.yaeyama.linerchecker.ui.compose.navigation.YaimafuniNavHost

@Composable
fun YaimafuniApp() {
    val navController = rememberNavController()
    YaimafuniScaffold(
        bottomBar = {
            MainNavigation(
                navController = navController
            )
        },
    ) {
        YaimafuniNavHost(
            navController = navController
        )
    }
}

@Preview
@Composable
private fun YaimafuniAppPreview() {
    YaimafuniApp()
}
