package com.yaeyama.linerchecker.ui.dashboard.component

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
import com.ikmr.banbara23.yaeyama_liner_checker.R
import com.yaeyama.linerchecker.ui.theme.YaimafuniAndroidTheme

@Composable
fun DashBoardAppBar(modifier: Modifier = Modifier) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = stringResource(id = R.string.app_name),
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
        // TODO: プレビューが表示されない
        DashBoardAppBar(
            modifier = Modifier.height(56.dp).fillMaxWidth(),
        )
    }
}
