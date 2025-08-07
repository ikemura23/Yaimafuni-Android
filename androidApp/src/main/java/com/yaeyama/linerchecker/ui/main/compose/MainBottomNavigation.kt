package com.yaeyama.linerchecker.ui.main.compose

import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
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
    BottomNavigation(
        backgroundColor = colorResource(id = R.color.see_blue),
        modifier = modifier,
    ) {
        // Dashboard Tab
        BottomNavigationItem(
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
            selectedContentColor = Color.White,
            unselectedContentColor = Color.White.copy(alpha = 0.6f),
        )

        // Weather Tab
        BottomNavigationItem(
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
            selectedContentColor = Color.White,
            unselectedContentColor = Color.White.copy(alpha = 0.6f),
        )

        // Typhoon Tab (with badge logic - will be implemented later)
        BottomNavigationItem(
            icon = {
                // TODO: Implement badge for typhoon count
                Icon(
                    painter = painterResource(id = R.drawable.ic_typhoon_white),
                    contentDescription = stringResource(id = R.string.title_typhoon),
                    modifier = Modifier.size(24.dp),
                )
            },
            label = {
                Text(text = stringResource(id = R.string.title_typhoon))
            },
            selected = selectedTab == MainTab.Typhoon,
            onClick = { onTabSelected(MainTab.Typhoon) },
            selectedContentColor = Color.White,
            unselectedContentColor = Color.White.copy(alpha = 0.6f),
        )
    }
}