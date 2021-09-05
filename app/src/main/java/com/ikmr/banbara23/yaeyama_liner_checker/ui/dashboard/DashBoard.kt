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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ikemura.shared.model.statusdetail.PortStatus
import com.ikemura.shared.model.statusdetail.Status
import com.ikemura.shared.model.top.Ports
import com.ikmr.banbara23.yaeyama_liner_checker.R
import com.ikmr.banbara23.yaeyama_liner_checker.ui.theme.StatusColor
import com.ikmr.banbara23.yaeyama_liner_checker.ui.theme.YaimafuniAndroidTheme

@Composable
fun DashBoard(ports: List<Ports>) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            DashBoardHeader()
            ports.forEach { p ->
                RowDivider()
                DashBoardRow(p)
            }
        }
    }
}

@Composable
fun RowDivider() {
    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .width(1.dp),
        color = colorResource(id = R.color.light_grey)
    )
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
    val dummyPorts: List<Ports> = (1..5).toList().map { dummyPort }
    YaimafuniAndroidTheme {
        Surface {
            DashBoard(dummyPorts)
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
        Spacer(modifier = Modifier.padding(4.dp))
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

@Composable
fun DashBoardRow(
    port: Ports,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(text = port.anei.portName)
        DashBoardRowItem(
            portName = "安栄観光",
            status = port.anei.status
        )
        DashBoardRowItem(
            portName = "八観フェ",
            status = port.ykf.status
        )
    }
}

@Preview(name = "Body Row")
@Composable
fun DashBoardRowPreview() {
    YaimafuniAndroidTheme {
        Surface {
            DashBoardRow(
                dummyPort
            )
        }
    }
}

private val dummyPort = Ports(
    anei = PortStatus(
        portCode = "hatoma",
        portName = "鳩間島航路",
        comment = "海上時化の為、全便欠航。",
        status = Status(
            text = "通常運航",
            code = "normal",
        )
    ),
    ykf = PortStatus(
        portCode = "hatoma",
        portName = "鳩間",
        comment = "海上時化の為、全便欠航。",
        status = Status(
            text = "欠航",
            code = "cancel",
        )
    )
)