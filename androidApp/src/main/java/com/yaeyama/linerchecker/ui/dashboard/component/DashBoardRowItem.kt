package com.yaeyama.linerchecker.ui.dashboard.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yaeyama.linerchecker.ui.portstatusdetail.component.getStatusBackgroundColor
import com.yaeyama.linerchecker.ui.theme.StatusColor
import com.yaeyama.linerchecker.ui.theme.YaimafuniAndroidTheme
import com.yaeyama_liner_checker.domain.statusdetail.Status

@Composable
fun DashBoardRowItem(
    modifier: Modifier,
    portName: String,
    status: Status,
) {
    val statusBackgroundColor = status.getStatusBackgroundColor()
    Column(
        modifier = modifier.padding(8.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (status.code.isNotEmpty()) {
            Text(
                text = portName,
                style = MaterialTheme.typography.body2,
            )
            Spacer(modifier = Modifier.padding(4.dp))
            Text(
                text = status.text,
                color = Color.White,
                style = MaterialTheme.typography.body2,
                modifier = Modifier
                    .background(
                        color = statusBackgroundColor,
                        shape = RoundedCornerShape(16),
                    )
                    .padding(4.dp),
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
                modifier = Modifier,
            )
        }
    }
}
