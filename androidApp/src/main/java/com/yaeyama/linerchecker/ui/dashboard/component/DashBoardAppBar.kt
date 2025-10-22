package com.yaeyama.linerchecker.ui.dashboard.component

import androidx.compose.foundation.background
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.ikmr.banbara23.yaeyama_liner_checker.BuildConfig
import com.ikmr.banbara23.yaeyama_liner_checker.R
import com.yaeyama.linerchecker.ui.theme.AppBackgroundColor
import com.yaeyama.linerchecker.ui.theme.YaimafuniAndroidTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashBoardAppBar(modifier: Modifier = Modifier) {
    val titleText = stringResource(R.string.app_name) + if (BuildConfig.DEBUG) BuildConfig.VERSION_NAME else ""
    TopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = titleText,
                color = Color.White,
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
        ),
    )
}

@Preview(showBackground = true)
@Composable
private fun DashBoardAppBarPreview() {
    YaimafuniAndroidTheme {
        DashBoardAppBar(
            modifier = Modifier
                .background(AppBackgroundColor),
        )
    }
}
