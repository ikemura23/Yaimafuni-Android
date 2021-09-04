package com.ikmr.banbara23.yaeyama_liner_checker.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ikemura.shared.model.top.Status
import com.ikmr.banbara23.yaeyama_liner_checker.R
import com.ikmr.banbara23.yaeyama_liner_checker.ui.theme.StatusColor
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

@Composable
fun DashBoardRowItem(
    portName: String,
    status: Status,
) {
    val statusBackgroundColor = status.getStatusBackgroundColor()
    Column(
        modifier = Modifier.padding(8.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = portName,
            style = MaterialTheme.typography.body2,
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Text(
            text = status.text,
            color = Color.White,
            style = MaterialTheme.typography.body2,
            modifier = Modifier
                .background(
                    color = statusBackgroundColor,
                    shape = RoundedCornerShape(16)
                )
                .padding(4.dp)
        )
    }
}

@Preview(name = "Body Row")
@Composable
fun DashBoardRowItemPreview() {
    val portName = "仮の港名"
    val status = Status("normal", text = "運行")
    YaimafuniAndroidTheme {
        Surface {
            DashBoardRowItem(
                portName = portName,
                status = status,
            )
        }
    }
}

// ステータスの背景色
fun Status.getStatusBackgroundColor() = when (this.code) {
    "nomal", "normal" -> StatusColor.Normal
    "cation" -> StatusColor.Cation
    "cancel" -> StatusColor.Cancel
    else -> StatusColor.Cation
}
