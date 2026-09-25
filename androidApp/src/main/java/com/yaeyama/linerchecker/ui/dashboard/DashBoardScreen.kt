package com.yaeyama.linerchecker.ui.dashboard

import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yaeyama.linerchecker.R
import com.yaeyama.linerchecker.domain.top.Ports
import com.yaeyama.linerchecker.ui.common.PreviewBox
import com.yaeyama.linerchecker.ui.common.YaimafuniScaffold
import com.yaeyama.linerchecker.ui.dashboard.component.DashBoardAppBar
import com.yaeyama.linerchecker.ui.portstatusdetail.PortStatusDetailActivity

@Composable
fun DashBoardScreenRoot(
    viewModel: DashBoardViewModel,
    modifier: Modifier = Modifier,
    // TODO: onRowClickをMainScreenに移動して、MainScreenからPortStatusDetailActivityを起動するようにする
) {
    LaunchedEffect(Unit) {
        viewModel.fetchPortList()
    }

    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    DashBoardScreen(
        uiState,
        modifier = modifier,
        onRowClick = { port ->
            // PortStatusDetailActivityへの遷移
            val intent = Intent(context, PortStatusDetailActivity::class.java).apply {
                putExtra(PortStatusDetailActivity.EXTRA_PORT_NAME, port.anei.portName)
                putExtra(PortStatusDetailActivity.EXTRA_PORT_CODE, port.anei.portCode)
            }
            context.startActivity(intent)
        },
        onRetry = viewModel::fetchPortList,
    )
}

@Composable
internal fun DashBoardScreen(
    uiState: DashBoardUiState,
    modifier: Modifier = Modifier,
    onRowClick: (Ports) -> Unit,
    onRetry: () -> Unit = {},
) {
    YaimafuniScaffold(
        topBar = { DashBoardAppBar() },
        // MainScreenのMainScaffold(bottomBar)が下端のinsetを既に確保しているため、二重に確保しない
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
    ) { paddingValues ->
        Box(
            modifier = modifier
                .padding(paddingValues) // Edge to edge対応のためのpaddingを追加
                .fillMaxSize(),
        ) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                uiState.isError -> {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = stringResource(R.string.dashboard_fetch_failed),
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
                        modifier = Modifier.verticalScroll(rememberScrollState()), // スクロール可能にする
                    ) {
                        DashBoardPage(
                            ports = uiState.portList,
                            onRowClick = onRowClick,
                            modifier = Modifier
                                .padding(horizontal = 16.dp) // 子コンポーネントに画面端からの余白
                                .padding(bottom = 16.dp), // スクロール時にコンテンツが見切れないように余白を追加
                        )
                    }
                }
            }
        }
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
    heightDp = 480,
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

@Preview(showBackground = true, name = "Loading Pattern")
@Composable
private fun DashBoardScreenLoadingPreview() {
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

@Preview(showBackground = true, name = "Error Pattern")
@Composable
private fun DashBoardScreenErrorPreview() {
    PreviewBox {
        DashBoardScreen(
            uiState = DashBoardUiState(
                isLoading = false,
                isError = true,
                portList = emptyList(),
            ),
            onRowClick = {},
        )
    }
}
