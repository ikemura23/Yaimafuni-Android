package com.yaeyama.linerchecker.ui.main.compose

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yaeyama.linerchecker.ui.dashboard.DashBoardScreenRoot
import com.yaeyama.linerchecker.ui.dashboard.DashBoardViewModel
import com.yaeyama.linerchecker.ui.main.MainViewModel
import com.yaeyama.linerchecker.ui.typhoon.list.TyphoonListViewModel
import com.yaeyama.linerchecker.ui.typhoon.list.compose.TyphoonListScreen
import com.yaeyama.linerchecker.ui.weather.WeatherScreen
import com.yaeyama.linerchecker.ui.weather.WeatherViewModel

@Composable
fun MainScreen(
    mainViewModel: MainViewModel,
    weatherViewModel: WeatherViewModel,
    dashboardViewModel: DashBoardViewModel,
    typhoonListViewModel: TyphoonListViewModel,
    modifier: Modifier = Modifier,
) {
    var selectedTab by remember { mutableStateOf(MainTab.Dashboard) }

    val typhoonCount by mainViewModel.typhoonCount.collectAsStateWithLifecycle()

    MainScaffold(
        bottomBar = {
            MainBottomNavigation(
                selectedTab = selectedTab,
                onTabSelected = { tab -> selectedTab = tab },
                typhoonBadgeCount = typhoonCount,
            )
        },
        // 各タブが自身のScaffold(topBar)でinsetを消費するため、ここでは二重に確保しない
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
    ) { paddingValues ->
        when (selectedTab) {
            MainTab.Dashboard -> {
                DashBoardScreenRoot(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    viewModel = dashboardViewModel
                )
            }

            MainTab.Weather -> {
                WeatherScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    weatherViewModel = weatherViewModel,
                )
            }

            MainTab.Typhoon -> {
                TyphoonListScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    viewModel = typhoonListViewModel
                )
            }
        }
    }
}

enum class MainTab {
    Dashboard,
    Weather,
    Typhoon,
}
