package com.yaeyama.linerchecker.ui.dashboard

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yaeyama.linerchecker.ui.theme.YaimafuniAndroidTheme
import com.yaeyama_liner_checker.domain.top.Ports

@Composable
fun DashBoardScreen(
    viewModel: DashBoardViewModel,
    modifier: Modifier = Modifier,
    onRowClick: (Ports) -> Unit,
) {
    val uiState: State<DashBoardUiState> = viewModel.uiState.collectAsState()
    DashBoardScreen(
        uiState.value,
        modifier = modifier,
        onRowClick = onRowClick,
    )
}

@Composable
private fun DashBoardScreen(
    uiState: DashBoardUiState,
    modifier: Modifier = Modifier,
    onRowClick: (Ports) -> Unit,
) {
    DashBoard(
        modifier = modifier.padding(16.dp),
        ports = uiState.portList,
        onRowClick = onRowClick,
    )
}

@Preview
@Composable
private fun DashBoardScreenPreview() {
    YaimafuniAndroidTheme {
        DashBoardScreen(
            uiState = DashBoardUiState(
                isLoading = false,
                isError = false,
                portList = FakeDashBoardDataProvider.dummyPortList,
            ),
            onRowClick = {},
        )
    }
}

@Preview
@Composable
private fun DashBoardScreenPreview2() {
    YaimafuniAndroidTheme {
        DashBoardScreen(
            uiState = DashBoardUiState(
                isLoading = true,
                isError = false,
                portList = emptyList(),
            ),
            onRowClick = {},
        )
    }
}
