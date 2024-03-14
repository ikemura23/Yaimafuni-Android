package com.ikemura23.yaeyama.linerchecker.ui.compose

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Screen(
    val route: String,
    val navArguments: List<NamedNavArgument> = emptyList()
) {
    data object Dashboard : Screen("dashboard")
    data object PortStatusDetail:Screen(
        route = "portStatusDetail/{routeId}",
        navArguments = listOf(navArgument("routeId") {
            type = NavType.StringType
        })
    ) {
        fun createRoute(routeId: String) = "portStatusDetail/$routeId"
    }
}