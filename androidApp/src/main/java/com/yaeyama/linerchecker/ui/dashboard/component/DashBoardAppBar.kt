package com.yaeyama.linerchecker.ui.dashboard.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ikmr.banbara23.yaeyama_liner_checker.BuildConfig
import com.ikmr.banbara23.yaeyama_liner_checker.R
import com.yaeyama.linerchecker.ui.theme.YaimafuniAndroidTheme

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
        backgroundColor = Color.Transparent,
        elevation = 0.dp,
    )
}

@Preview(showBackground = true)
@Composable
private fun DashBoardAppBarPreview() {
    YaimafuniAndroidTheme {
        DashBoardAppBar(
            modifier = Modifier
                .height(56.dp)
                .fillMaxWidth()
                .background(Color.Blue),
        )
    }
}
