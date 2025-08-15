package com.yaeyama.linerchecker.ui.main.compose

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ikmr.banbara23.yaeyama_liner_checker.R

@Composable
fun MainBottomNavigation(
    selectedTab: MainTab,
    onTabSelected: (MainTab) -> Unit,
    typhoonBadgeCount: Int,
    modifier: Modifier = Modifier,
) {
    NavigationBar(
        containerColor = colorResource(id = R.color.see_blue),
        modifier = modifier,
    ) {
        // Dashboard Tab
        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_directions_boat_white),
                    contentDescription = stringResource(id = R.string.title_home),
                    modifier = Modifier.size(24.dp),
                )
            },
            label = {
                Text(text = stringResource(id = R.string.title_home))
            },
            selected = selectedTab == MainTab.Dashboard,
            onClick = { onTabSelected(MainTab.Dashboard) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                selectedTextColor = Color.White,
                unselectedIconColor = Color.White.copy(alpha = 0.6f),
                unselectedTextColor = Color.White.copy(alpha = 0.6f),
                indicatorColor = Color.White.copy(alpha = 0.2f)
            )
        )

        // Weather Tab
        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_weather_white),
                    contentDescription = stringResource(id = R.string.title_weather),
                    modifier = Modifier.size(24.dp),
                )
            },
            label = {
                Text(text = stringResource(id = R.string.title_weather))
            },
            selected = selectedTab == MainTab.Weather,
            onClick = { onTabSelected(MainTab.Weather) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                selectedTextColor = Color.White,
                unselectedIconColor = Color.White.copy(alpha = 0.6f),
                unselectedTextColor = Color.White.copy(alpha = 0.6f),
                indicatorColor = Color.White.copy(alpha = 0.2f)
            )
        )

        // Typhoon Tab (with badge)
        NavigationBarItem(
            icon = {
                BadgedBox(
                    badge = {
                        if (typhoonBadgeCount > 0) {
                            Badge {
                                Text(text = typhoonBadgeCount.toString())
                            }
                        }
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_typhoon_white),
                        contentDescription = stringResource(id = R.string.title_typhoon),
                        modifier = Modifier.size(24.dp),
                    )
                }
            },
            label = {
                Text(text = stringResource(id = R.string.title_typhoon))
            },
            selected = selectedTab == MainTab.Typhoon,
            onClick = { onTabSelected(MainTab.Typhoon) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                selectedTextColor = Color.White,
                unselectedIconColor = Color.White.copy(alpha = 0.6f),
                unselectedTextColor = Color.White.copy(alpha = 0.6f),
                indicatorColor = Color.White.copy(alpha = 0.2f)
            )
        )
    }
}