package com.yaeyama.linerchecker.ui.main.compose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.yaeyama.linerchecker.ui.dashboard.DashBoardViewModel
import com.yaeyama.linerchecker.ui.dashboard.compose.DashboardScreen
import com.yaeyama.linerchecker.ui.main.MainViewModel
import com.yaeyama.linerchecker.ui.theme.YaimafuniAndroidTheme
import com.yaeyama.linerchecker.ui.weather.WeatherScreen
import com.yaeyama.linerchecker.ui.weather.WeatherScreenViewModel

@Composable
fun MainScreen(
    mainViewModel: MainViewModel,
    weatherViewModel: WeatherScreenViewModel,
    dashboardViewModel: DashBoardViewModel,
    onTyphoonBadgeCountChanged: (Int) -> Unit,
) {
    var selectedTab by remember { mutableStateOf(MainTab.Dashboard) }
    
    // 台風バッジカウントの監視
    val typhoonCount by mainViewModel.existsTyphoon().collectAsState(initial = 0)
    
    // カウント変更をActivityに通知
    onTyphoonBadgeCountChanged(typhoonCount)

    Scaffold(
        backgroundColor = Color.Transparent,
        bottomBar = {
            MainBottomNavigation(
                selectedTab = selectedTab,
                onTabSelected = { tab -> selectedTab = tab },
                typhoonBadgeCount = typhoonCount,
            )
        },
    ) { paddingValues ->
        when (selectedTab) {
            MainTab.Dashboard -> {
                DashboardScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    viewModel = dashboardViewModel
                )
            }
            MainTab.Weather -> {
                WeatherScreen(
                    weatherViewModel = weatherViewModel,
                )
            }
            MainTab.Typhoon -> {
                TyphoonPlaceholder(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
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