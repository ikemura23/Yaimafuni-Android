package com.yaeyama.linerchecker.ui.dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yaeyama.linerchecker.ui.dashboard.component.DashBoardHeader
import com.yaeyama.linerchecker.ui.dashboard.component.DashBoardRow
import com.yaeyama.linerchecker.ui.theme.DashBoardRowDividerColor
import com.yaeyama.linerchecker.ui.theme.YaimafuniAndroidTheme
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
        color = DashBoardRowDividerColor,
    )
}

@Preview
@Composable
private fun DashBoardPreview() {
    val dummyPorts = FakeDashBoardDataProvider.dummyPortList
    YaimafuniAndroidTheme {
        Surface {
            DashBoardPage(ports = dummyPorts) {}
        }
    }
}
