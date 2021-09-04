package com.ikmr.banbara23.yaeyama_liner_checker.ui.dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ikmr.banbara23.yaeyama_liner_checker.R
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

@Preview(name = "ヘッダー")
@Composable
fun DashBoardHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .requiredHeight(48.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.padding(8.dp))
        Icon(
            modifier = Modifier.size(24.dp),
            painter = painterResource(id = R.drawable.ic_harbor),
            contentDescription = null,
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Text(text = stringResource(id = R.string.dash_board_title))
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