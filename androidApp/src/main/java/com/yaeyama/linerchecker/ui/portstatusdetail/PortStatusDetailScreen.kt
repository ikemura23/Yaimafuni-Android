package com.yaeyama.linerchecker.ui.portstatusdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
// TODO: コンポーネント名がScreenではないのでリネームしたい
@Composable
fun PortStatusDetailScreen(
    modifier: Modifier = Modifier,
    company: Company,
    portCode: String,
    viewModel: PortStatusDetailViewModel,
) {
    LaunchedEffect(company, portCode) {
        viewModel.fetchDetail(company, portCode)
    }
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    PortStatusDetailScreen(
        modifier = modifier,
        isLoading = uiState.value.isLoading,
        isError = uiState.value.isError,
        isTimeTableError = uiState.value.isTimeTableError,
        portName = uiState.value.portStatus.portName,
        status = uiState.value.portStatus.status,
        statusDescription = uiState.value.portStatus.comment,
        timeTable = uiState.value.timeTable,
        onRetry = { viewModel.fetchDetail(company, portCode) },
    )
}

@Composable
private fun PortStatusDetailScreen(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    isError: Boolean = false,
    isTimeTableError: Boolean = false,
    portName: String,
    status: Status,
    statusDescription: String,
    timeTable: TimeTable,
    onRetry: () -> Unit = {},
) {
    Box(modifier = modifier.fillMaxSize()) {
        when {
            isLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            isError -> {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = "運行情報の取得に失敗しました",
                        style = MaterialTheme.typography.bodyLarge,
                    )
                    Button(
                        modifier = Modifier.padding(top = 16.dp),
                        onClick = onRetry,
                    ) {
                        Text(text = "再試行")
                    }
                }
            }
            else -> {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState()),
                ) {
                    PortMainStatus(
                        portName = portName,
                        status = status,
                        statusDescription = statusDescription,
                    )

                    Spacer(modifier = Modifier.size(16.dp))

                    if (isTimeTableError) {
                        Text(
                            text = "時刻表の取得に失敗しました",
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    } else {
                        TimeTableList(
                            timeTable = timeTable,
                        )
                    }
                }
            }
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
            modifier = Modifier.background(Color.Blue),
            portName = "portName",
            status = Status(code = "normal", text = "text"),
            statusDescription = "statusDescription",
            timeTable = dummyTimeTable,
        )
    }
}

@Preview(name = "Loading Pattern")
@Composable
private fun PortStatusDetailScreenLoadingPreview() {
    YaimafuniAndroidTheme {
        PortStatusDetailScreen(
            isLoading = true,
            portName = "",
            status = Status(),
            statusDescription = "",
            timeTable = TimeTable(),
        )
    }
}

@Preview(name = "Error Pattern")
@Composable
private fun PortStatusDetailScreenErrorPreview() {
    YaimafuniAndroidTheme {
        PortStatusDetailScreen(
            isError = true,
            portName = "",
            status = Status(),
            statusDescription = "",
            timeTable = TimeTable(),
        )
    }
}

@Preview(name = "TimeTable Error Pattern")
@Composable
private fun PortStatusDetailScreenTimeTableErrorPreview() {
    YaimafuniAndroidTheme {
        PortStatusDetailScreen(
            isTimeTableError = true,
            portName = "portName",
            status = Status(code = "normal", text = "text"),
            statusDescription = "statusDescription",
            timeTable = TimeTable(),
        )
    }
}
