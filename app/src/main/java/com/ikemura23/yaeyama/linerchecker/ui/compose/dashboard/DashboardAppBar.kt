package com.ikemura23.yaeyama.linerchecker.ui.compose.dashboard

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.ikemura23.yaeyama.linerchecker.BuildConfig
import com.ikemura23.yaeyama.linerchecker.R
import com.ikemura23.yaeyama.linerchecker.ui.theme.YaimafuniAndroidTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardAppBar(modifier: Modifier = Modifier) {
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
            containerColor = Color.Transparent // 透明な背景色を指定
        )
    )
}

@Preview
@Composable
private fun DashboardAppBarPreview() {
    YaimafuniAndroidTheme {
        DashboardAppBar()
    }
}
