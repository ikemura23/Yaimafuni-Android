package com.ikemura23.yaeyama.linerchecker.ui.compose.navigation

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ikemura23.yaeyama.linerchecker.ui.theme.YaimafuniAndroidTheme

@Composable
fun MainNavigation(
    modifier: Modifier = Modifier,
    destinations: List<MainDestination> = MainDestination.entries,
    navController: NavHostController,
) {
    val backStackEntry: NavBackStackEntry? by navController.currentBackStackEntryAsState()
    val currentDestination: NavDestination? = backStackEntry?.destination

    NavigationBar(
        modifier = modifier,
        tonalElevation = 0.dp,
        containerColor = Color(0xFF007AB7),
    ) {
        destinations.forEach { destination: MainDestination ->
            val selected = currentDestination?.hierarchy?.any { it.route == destination.route } == true
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = destination.iconDrawableId),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = {
                    Text(
                        text = stringResource(id = destination.iconTextId),
                    )
                },
                onClick = {
                    navController.navigate(destination.route)
                },
                selected = true,
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color(0xFF007AB7),
                    selectedIconColor = Color.White,
                    unselectedIconColor = Color(0x4Cffffff),
                    selectedTextColor = Color.White,
                    unselectedTextColor = Color(0x4Cffffff),
                )
            )
        }
    }
}

@Preview
@Composable
private fun NavigationBarPreview() {
    YaimafuniAndroidTheme {
        val navController = rememberNavController()
        MainNavigation(
            navController = navController
        )
    }
}
