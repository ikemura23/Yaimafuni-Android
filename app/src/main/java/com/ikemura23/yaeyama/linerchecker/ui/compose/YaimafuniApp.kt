package com.ikemura23.yaeyama.linerchecker.ui.compose

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ikemura23.yaeyama.linerchecker.ui.compose.dashboard.DashboardScreen

@Composable
fun YaimafuniApp() {
    val navController = rememberNavController()
    YaimafuniNavHost(
        navController = navController
    )
}

@Composable
fun YaimafuniNavHost(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = Screen.Dashboard.route) {
        composable(route = Screen.Dashboard.route) {
            DashboardScreen(
                onRowClick = {
                    // TODO: 運行詳細への遷移
                    // navController.navigate()
                }
            )
        }
    }
}