package com.yaeyama.linerchecker.ui.portstatusdetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yaeyama.linerchecker.ui.portstatusdetail.component.PortMainStatus
import com.yaeyama.linerchecker.ui.portstatusdetail.component.TimeRow
import com.yaeyama.linerchecker.ui.portstatusdetail.component.TimeTableList
import com.yaeyama.linerchecker.ui.theme.YaimafuniAndroidTheme
import com.yaeyama_liner_checker.domain.statusdetail.Company
import com.yaeyama_liner_checker.domain.statusdetail.Status
import com.yaeyama_liner_checker.domain.time_table.Header
import com.yaeyama_liner_checker.domain.time_table.RowItem
import com.yaeyama_liner_checker.domain.time_table.TimeTable

/**
 * 運行詳細スクリーン
 */

@Composable
fun PortStatusDetailScreen(
    company: Company,
    portCode: String,
    viewModel: PortStatusDetailViewModel,
) {
    val uiState = viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        // TODO: viewModelでfetchを呼ぶ
    }

    // TODO: PortStatusDetailScreen に uiState を渡す
}

@Composable
private fun PortStatusDetailScreen(
    portName: String,
    status: Status,
    statusDescription: String,
    timeTable: TimeTable,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Column {
            // PortMainStatus()
            PortMainStatus(
                portName = portName,
                status = status,
                statusDescription = statusDescription,
            )

            Spacer(modifier = Modifier.size(16.dp))

            TimeTableList(
                timeTable = timeTable,
            )
        }
    }
}

@Preview
@Composable
private fun PortStatusDetailScreenPreview() {
    val row = TimeRow(
        left = RowItem(
            status = Status(code = "nomal", text = "通常運行"),
            time = "00:00",
            memo = "",
        ),
        right = RowItem(
            status = Status(code = "cancel", text = "通常運行"),
            time = "00:00",
            memo = "",
        ),
    )
    val rows = List(5) { row }
    val dummyTimeTable = TimeTable(
        header = Header(left = "石垣島", right = "大原港"),
        row = rows,
    )

    YaimafuniAndroidTheme {
        PortStatusDetailScreen(
            portName = "portName",
            status = Status(code = "normal", text = "text"),
            statusDescription = "statusDescription",
            timeTable = dummyTimeTable,
        )
    }
}
