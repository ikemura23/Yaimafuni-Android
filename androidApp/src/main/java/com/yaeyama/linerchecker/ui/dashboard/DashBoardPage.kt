package com.yaeyama.linerchecker.ui.dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ikmr.banbara23.yaeyama_liner_checker.R
import com.yaeyama.linerchecker.ui.dashboard.component.DashBoardHeader
import com.yaeyama.linerchecker.ui.dashboard.component.DashBoardRow
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
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.white),
        ),
    ) {
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState()), // スクロール可能にする
        ) {
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
    HorizontalDivider(
        modifier = Modifier
            .fillMaxWidth()
            .width(1.dp),
        color = colorResource(id = R.color.light_grey),
    )
}

@Preview(showBackground = true)
@Composable
private fun DashBoardPreview() {
    val dummyPorts = FakeDashBoardDataProvider.dummyPortList
    YaimafuniAndroidTheme {
        DashBoardPage(ports = dummyPorts) {}
    }
}
