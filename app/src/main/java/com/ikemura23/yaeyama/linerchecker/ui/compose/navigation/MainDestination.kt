package com.ikemura23.yaeyama.linerchecker.ui.compose.navigation

import com.ikemura23.yaeyama.linerchecker.R

/**
 * ボトムナビゲーションの表示アイコン, 文字列を管理するEnum
 */
enum class MainDestination(
    val route: String,
    val iconDrawableId: Int,
    val iconTextId: Int,
) {
    DASHBOARD(
        route = "dashboard",
        iconDrawableId = R.drawable.ic_boat_white,
        iconTextId = R.string.title_home,
    ),
    WEATHER(
        route = "dashboard", // weather
        iconDrawableId = R.drawable.ic_weather_white,
        iconTextId = R.string.title_weather,
    ),
    TYPHOON(
        route = "dashboard", // typhoon
        iconDrawableId = R.drawable.ic_typhoon_white,
        iconTextId = R.string.title_typhoon,
    ),
}