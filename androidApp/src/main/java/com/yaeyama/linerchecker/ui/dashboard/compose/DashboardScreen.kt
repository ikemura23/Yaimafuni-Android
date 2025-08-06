package com.yaeyama.linerchecker.ui.dashboard.compose

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.yaeyama.linerchecker.ui.dashboard.DashBoardScreen as ExistingDashBoardScreen
import com.yaeyama.linerchecker.ui.dashboard.DashBoardViewModel
import com.yaeyama.linerchecker.ui.portstatusdetail.PortStatusDetailActivity
import com.yaeyama_liner_checker.domain.top.Ports
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * DashboardScreen for MainScreen integration
 * 既存DashBoardScreenを活用し、MainActivity統合用にラップ
 */
@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    viewModel: DashBoardViewModel? = null,
) {
    val context = LocalContext.current
    
    // PortStatusDetailActivity起動用のランチャー
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        // 結果処理が必要な場合はここで処理
    }

    // ViewModelがnullの場合は何も表示しない
    viewModel?.let { vm ->
        // 既存のDashBoardScreenをそのまま使用
        ExistingDashBoardScreen(
            viewModel = vm,
            modifier = modifier,
            onRowClick = { port ->
                // PortStatusDetailActivityへの遷移（Safe Args互換）
                val intent = Intent(context, PortStatusDetailActivity::class.java).apply {
                    putExtra("portName", port.anei.portName)
                    putExtra("portCode", port.anei.portCode)
                }
                launcher.launch(intent)
            }
        )
    }
}