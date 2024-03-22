package com.ikemura23.yaeyama.linerchecker.ui.compose.dashboard

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.ikemura23.yaeyama.linerchecker.R
import com.ikemura23.yaeyama.linerchecker.ui.theme.YaimafuniAndroidTheme

@Composable
fun DashboardPage(
    modifier: Modifier = Modifier,
    onRowClick: () -> Unit,
) {
    Button(
        modifier = modifier,
        onClick = onRowClick
    ) {
        Greeting(
            stringResource(R.string.app_name),
        )
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFF)
@Composable
fun GreetingPreview() {
    YaimafuniAndroidTheme {
        DashboardPage(
            onRowClick = {},
        )
    }
}
