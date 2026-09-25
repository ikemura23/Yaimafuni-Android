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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yaeyama.linerchecker.R
import com.yaeyama.linerchecker.domain.statusdetail.Company
import com.yaeyama.linerchecker.domain.statusdetail.Status
import com.yaeyama.linerchecker.domain.timetable.Header
import com.yaeyama.linerchecker.domain.timetable.RowItem
import com.yaeyama.linerchecker.domain.timetable.TimeTable
import com.yaeyama.linerchecker.ui.common.compose.ErrorContent
import com.yaeyama.linerchecker.ui.common.compose.FullScreenErrorContent
import com.yaeyama.linerchecker.ui.common.compose.LoadingContent
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
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    PortStatusDetailScreen(
        modifier = modifier,
        isLoading = uiState.isLoading,
        errorMessageRes = uiState.errorMessageRes,
        timeTableErrorMessageRes = uiState.timeTableErrorMessageRes,
        portName = uiState.portStatus.portName,
        status = uiState.portStatus.status,
        statusDescription = uiState.portStatus.comment,
        timeTable = uiState.timeTable,
        onRetry = viewModel::retry,
    )
}

@Composable
internal fun PortStatusDetailScreen(
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
                LoadingContent()
            }
            errorMessageRes != null -> {
                FullScreenErrorContent(
                    messageRes = errorMessageRes,
                    onRetry = onRetry,
                )
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
                        ErrorContent(
                            messageRes = timeTableErrorMessageRes,
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            onRetry = onRetry,
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
