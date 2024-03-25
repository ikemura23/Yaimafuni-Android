package com.ikemura23.yaeyama.linerchecker.ui.compose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ikemura23.yaeyama.linerchecker.ui.compose.dashboard.DashboardScreen
import com.ikemura23.yaeyama.linerchecker.ui.compose.portstatusdetail.LinerStatusDetailScreen
import com.ikemura23.yaeyama.linerchecker.ui.compose.typhoon.TyphoonScreen
import com.ikemura23.yaeyama.linerchecker.ui.compose.weather.WeatherScreen

@Composable
fun YaimafuniNavHost(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = Screen.Dashboard.route) {
        composable(route = Screen.Dashboard.route) {
            DashboardScreen(
                onRowClick = {
                    // TODO: routeId を引数にわたす
                    navController.navigate(
                        Screen.PortStatusDetail.createRoute("tempRouteId")
                    )
                }
            )
        }

        composable(
            route = Screen.PortStatusDetail.route,
            arguments = Screen.PortStatusDetail.navArguments,
        ) {
            LinerStatusDetailScreen()
        }

        composable(
            route = Screen.Weather.route,
        ) {
            WeatherScreen()
        }

        composable(
            route = Screen.Typhoon.route,
        ) {
            TyphoonScreen()
        }

        composable(route = Screen.Dashboard.route) {
            DashboardScreen(
                onRowClick = {
                    // TODO: routeId を引数にわたす
                    navController.navigate(
                        Screen.PortStatusDetail.createRoute("tempRouteId")
                    )
                }
            )
        }
    }
}
