package com.yaeyama.linerchecker.ui.dashboard

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yaeyama.linerchecker.ui.common.PreviewBox
import com.yaeyama.linerchecker.ui.dashboard.component.DashBoardAppBar
import com.yaeyama.linerchecker.ui.portstatusdetail.PortStatusDetailActivity
import com.yaeyama.linerchecker.ui.theme.SeeBlue
import com.yaeyama_liner_checker.domain.top.Ports

@Composable
fun DashBoardScreenRoot(
    viewModel: DashBoardViewModel,
    modifier: Modifier = Modifier,
    // TODO: onRowClickをMainScreenに移動して、MainScreenからPortStatusDetailActivityを起動するようにする
) {
    LaunchedEffect(Unit) {
        viewModel.fetchPortList()
    }

    // PortStatusDetailActivity起動用のランチャー
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        // 結果処理が必要な場合はここで処理
    }

    val context = LocalContext.current
    val uiState: State<DashBoardUiState> = viewModel.uiState.collectAsState()

    DashBoardScreen(
        uiState.value,
        modifier = modifier,
        onRowClick = { port ->
            // PortStatusDetailActivityへの遷移
            val intent = Intent(context, PortStatusDetailActivity::class.java).apply {
                putExtra(PortStatusDetailActivity.EXTRA_PORT_NAME, port.anei.portName)
                putExtra(PortStatusDetailActivity.EXTRA_PORT_CODE, port.anei.portCode)
            }
            launcher.launch(intent)
        }
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
        containerColor = SeeBlue,
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

@Preview(showBackground = true)
@Composable
private fun DashBoardScreenPreview() {
    PreviewBox {
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

@Preview(
    name = "Small Phone",
    showBackground = false,
    widthDp = 320,
    heightDp = 480
)
@Composable
private fun DashBoardScreenSmallDevicePreview() {
    PreviewBox {
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

@Preview(showBackground = true, name = "Error Pattern")
@Composable
private fun DashBoardScreenPreview2() {
    PreviewBox {
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
