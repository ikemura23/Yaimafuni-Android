package com.yaeyama.linerchecker.ui.dashboard

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yaeyama.linerchecker.ui.dashboard.component.DashBoardAppBar
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
    Scaffold(
        topBar = { DashBoardAppBar() },
        backgroundColor = Color.Transparent,
    ) {
        DashBoardPage(
            modifier = modifier.padding(
                horizontal = 16.dp,
                vertical = it.calculateTopPadding() + 16.dp,
            ),
            ports = uiState.portList,
            onRowClick = onRowClick,
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0000FF)
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

@Preview(showBackground = true, backgroundColor = 0xFF0000FF, name = "Error Pattern")
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
