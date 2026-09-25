package com.yaeyama.linerchecker.ui.portstatusdetail

import androidx.annotation.StringRes
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yaeyama.linerchecker.R
import com.yaeyama.linerchecker.domain.statusdetail.Company
import com.yaeyama.linerchecker.domain.statusdetail.Status
import com.yaeyama.linerchecker.domain.timetable.Header
import com.yaeyama.linerchecker.domain.timetable.RowItem
import com.yaeyama.linerchecker.domain.timetable.TimeTable
import com.yaeyama.linerchecker.ui.portstatusdetail.component.PortMainStatus
import com.yaeyama.linerchecker.ui.portstatusdetail.component.TimeRow
import com.yaeyama.linerchecker.ui.portstatusdetail.component.TimeTableList
import com.yaeyama.linerchecker.ui.theme.YaimafuniAndroidTheme
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
        errorMessageRes = uiState.value.errorMessageRes,
        timeTableErrorMessageRes = uiState.value.timeTableErrorMessageRes,
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
    @StringRes errorMessageRes: Int? = null,
    @StringRes timeTableErrorMessageRes: Int? = null,
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
            errorMessageRes != null -> {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = stringResource(errorMessageRes),
                        style = MaterialTheme.typography.bodyLarge,
                    )
                    Button(
                        modifier = Modifier.padding(top = 16.dp),
                        onClick = onRetry,
                    ) {
                        Text(text = stringResource(R.string.retry))
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

                    if (timeTableErrorMessageRes != null) {
                        Text(
                            text = stringResource(timeTableErrorMessageRes),
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
            errorMessageRes = R.string.port_status_fetch_failed,
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
            timeTableErrorMessageRes = R.string.time_table_not_found,
            portName = "portName",
            status = Status(code = "normal", text = "text"),
            statusDescription = "statusDescription",
            timeTable = TimeTable(),
        )
    }
}
