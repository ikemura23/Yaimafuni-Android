package com.ikemura23.yaeyama.linerchecker.ui.compose.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

/**
 * 画面（Screenコンポーザブル）を管理するクラス
 */
sealed class Screen(
    val route: String,
    val navArguments: List<NamedNavArgument> = emptyList()
) {
    data object Dashboard : Screen("dashboard")
    data object PortStatusDetail : Screen(
        route = "portStatusDetail/{routeId}",
        navArguments = listOf(navArgument("routeId") {
            type = NavType.StringType
        })
    ) {
        fun createRoute(routeId: String) = "portStatusDetail/$routeId"
    }

    data object Weather : Screen("weather")
    data object Typhoon : Screen("typhoon")
}
