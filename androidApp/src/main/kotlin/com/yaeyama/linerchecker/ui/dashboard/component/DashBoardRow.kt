package com.yaeyama.linerchecker.ui.dashboard.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yaeyama.linerchecker.ui.dashboard.FakeDashBoardDataProvider
import com.yaeyama.linerchecker.ui.theme.YaimafuniAndroidTheme
import com.yaeyama_liner_checker.domain.top.Ports

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
private fun DashBoardRowPreview() {
    YaimafuniAndroidTheme {
        Surface {
            DashBoardRow(
                FakeDashBoardDataProvider.dummyPort1,
            ) {}
        }
    }
}
