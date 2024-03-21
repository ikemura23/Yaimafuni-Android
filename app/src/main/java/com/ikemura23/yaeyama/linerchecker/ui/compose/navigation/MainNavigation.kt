package com.ikemura23.yaeyama.linerchecker.ui.compose.navigation

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ikemura23.yaeyama.linerchecker.R
import com.ikemura23.yaeyama.linerchecker.ui.theme.YaimafuniAndroidTheme

enum class MainDestination(
    val iconDrawableId: Int,
    val iconTextId: Int,
) {
    DASHBOARD(
        iconDrawableId = R.drawable.ic_boat_white,
        iconTextId = R.string.title_home,
    ),
    WEATHER(
        iconDrawableId = R.drawable.ic_weather_white,
        iconTextId = R.string.title_weather,
    ),
    TYPHOON(
        iconDrawableId = R.drawable.ic_typhoon_white,
        iconTextId = R.string.title_typhoon,
    ),
}

@Composable
fun MainNavigation(
    modifier: Modifier = Modifier,
    destinations: List<MainDestination> = MainDestination.entries,
) {
    NavigationBar(
        modifier = modifier,
        tonalElevation = 0.dp,
        containerColor = Color(0xFF007AB7),
    ) {
        destinations.forEach { destination ->
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
                onClick = {},
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
        MainNavigation(

        )
    }
}