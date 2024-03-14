package com.ikemura23.yaeyama.linerchecker.ui.compose.dashboard

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ikemura23.yaeyama.linerchecker.ui.theme.YaimafuniAndroidTheme

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    onRowClick: () -> Unit,
) {

    Scaffold(
        modifier = modifier,
        topBar = {
            DashboardAppBar()
        },
        containerColor = Color.Transparent,
    ) {contentPadding ->
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
