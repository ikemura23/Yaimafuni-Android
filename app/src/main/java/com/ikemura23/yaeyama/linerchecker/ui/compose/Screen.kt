package com.ikemura23.yaeyama.linerchecker.ui.compose

import androidx.navigation.NamedNavArgument

sealed class Screen(
    val route: String,
    val navArguments: List<NamedNavArgument> = emptyList()
) {
    data object Dashboard : Screen("dashboard")
}