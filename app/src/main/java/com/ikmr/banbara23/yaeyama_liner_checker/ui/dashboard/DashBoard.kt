package com.ikmr.banbara23.yaeyama_liner_checker.ui.dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ikmr.banbara23.yaeyama_liner_checker.ui.theme.YaimafuniAndroidTheme

@Composable
fun DashBoard() {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {

        }
    }
}

@Preview
@Composable
fun DashBoardPreview() {
    YaimafuniAndroidTheme {
        Surface {
            DashBoard()
        }
    }
}