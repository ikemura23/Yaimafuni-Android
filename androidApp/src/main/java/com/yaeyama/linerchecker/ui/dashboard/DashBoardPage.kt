package com.yaeyama.linerchecker.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import com.ikmr.banbara23.yaeyama_liner_checker.R
import com.yaeyama.linerchecker.ui.theme.StatusColor
import com.yaeyama.linerchecker.ui.theme.YaimafuniAndroidTheme
import com.yaeyama_liner_checker.domain.statusdetail.Status
import com.yaeyama_liner_checker.domain.top.Ports

@Composable
fun DashBoardPage(
    modifier: Modifier = Modifier,
    ports: List<Ports>,
    onRowClick: (Ports) -> Unit,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
    ) {
        Column {
            DashBoardHeader()
            ports.forEach { p ->
                RowDivider()
                DashBoardRow(p, onRowClick)
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
        color = colorResource(id = R.color.light_grey),
    )
}

@Preview(name = "ヘッダー")
@Composable
fun DashBoardHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .requiredHeight(48.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Spacer(modifier = Modifier.padding(8.dp))
        Icon(
            modifier = Modifier.size(24.dp),
            painter = painterResource(id = R.drawable.ic_harbor),
            contentDescription = null,
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Text(text = stringResource(id = R.string.dash_board_title))

        // TODO: ProgressBarを配置する（dash_board_fragment.xml の id/port_progressbar を参照）
    }
}

@Preview
@Composable
fun DashBoardPreview() {
    val dummyPorts = FakeDashBoardDataProvider.dummyPortList
    YaimafuniAndroidTheme {
        Surface {
            DashBoardPage(ports = dummyPorts) {}
        }
    }
}

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
    onRowClick: (Ports) -> Unit,
) {
    Box(
        modifier = Modifier
            .clickable(onClick = { onRowClick(port) })
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
    ) {
        Text(
            text = port.anei.portName,
            modifier = Modifier.align(Alignment.CenterStart),
        )
        DashBoardRowItem(
            modifier = Modifier.align(Alignment.Center),
            portName = "安栄観光",
            status = port.anei.status,
        )
        DashBoardRowItem(
            modifier = Modifier.align(Alignment.CenterEnd),
            portName = "八観フェ",
            status = port.ykf.status,
        )
    }
}

@Preview(name = "Body Row")
@Composable
fun DashBoardRowPreview() {
    YaimafuniAndroidTheme {
        Surface {
            DashBoardRow(
                FakeDashBoardDataProvider.dummyPort1,
            ) {}
        }
    }
}
