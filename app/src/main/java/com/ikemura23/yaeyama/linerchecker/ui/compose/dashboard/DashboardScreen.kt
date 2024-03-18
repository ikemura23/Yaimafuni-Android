package com.ikemura23.yaeyama.linerchecker.ui.compose.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ikemura23.yaeyama.linerchecker.R
import com.ikemura23.yaeyama.linerchecker.ui.compose.YaimafuniScaffold
import com.ikemura23.yaeyama.linerchecker.ui.theme.BackgroundSkyColor
import com.ikemura23.yaeyama.linerchecker.ui.theme.YaimafuniAndroidTheme

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    onRowClick: () -> Unit,
) {
    YaimafuniScaffold(
        modifier = modifier,
        topBar = {
            DashboardAppBar()
        },
        containerColor = BackgroundSkyColor,
    ) { contentPadding ->
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = BackgroundSkyColor,
        ) {
            Image(
                painter = painterResource(id = R.drawable.sun),
                contentDescription = null,
                alignment = Alignment.TopEnd,
                modifier = Modifier.size(200.dp), // 画像サイズ
                contentScale = ContentScale.Fit // アスペクト比を維持

            )
        }
        DashboardPage(
            modifier = Modifier.padding(top = contentPadding.calculateTopPadding()),
            onRowClick = onRowClick,
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0000FF)
@Composable
private fun DashboardScreenPreview() {
    YaimafuniAndroidTheme {
        DashboardScreen(
            onRowClick = {},
        )
    }
}
