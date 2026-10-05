package com.yaeyama.linerchecker.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.yaeyama.linerchecker.ui.main.compose.MainScreen
import com.yaeyama.linerchecker.ui.portstatusdetail.compose.PortStatusDetailScreen
import com.yaeyama.linerchecker.ui.typhoon.detail.compose.TyphoonDetailScreen
import com.yaeyama.linerchecker.ui.typhoon.detail.toTyphoonDetailUiModel
import org.koin.androidx.compose.koinViewModel

/**
 * アプリ全体の画面遷移
 * ViewModel は NavEntry ごとに作られ、その画面がバックスタックから外れると破棄される
 *
 * @param backStack 画面のバックスタック。画面回転やプロセスの再生成後も復元される
 */
@Composable
fun AppNavDisplay(
    modifier: Modifier = Modifier,
    backStack: NavBackStack<NavKey> = rememberNavBackStack(Main),
) {
    NavDisplay(
        backStack = backStack,
        modifier = modifier,
        onBack = { backStack.removeLastOrNull() },
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator(),
        ),
        entryProvider = entryProvider {
            entry<Main> {
                MainScreen(
                    mainViewModel = koinViewModel(),
                    weatherViewModel = koinViewModel(),
                    dashboardViewModel = koinViewModel(),
                    typhoonListViewModel = koinViewModel(),
                    onPortClick = { port ->
                        backStack.navigateFromMain(
                            PortStatusDetail(
                                portCode = port.anei.portCode,
                                portName = port.anei.portName,
                            ),
                        )
                    },
                    onTyphoonClick = { typhoon ->
                        backStack.navigateFromMain(TyphoonDetail(typhoon.toTyphoonDetailUiModel()))
                    },
                )
            }
            entry<PortStatusDetail> { key ->
                PortStatusDetailScreen(
                    portCode = key.portCode,
                    portName = key.portName,
                    viewModel = koinViewModel(),
                    onBackPressed = { backStack.removeLastOrNull() },
                )
            }
            entry<TyphoonDetail> { key ->
                TyphoonDetailScreen(
                    typhoon = key.typhoon,
                    onBackPressed = { backStack.removeLastOrNull() },
                )
            }
        },
    )
}

/**
 * ホーム画面から詳細画面へ進む
 * 連打や遷移アニメーション中のタップで詳細画面が重複して積まれないよう、ホーム画面が最前面のときだけ追加する
 */
private fun NavBackStack<NavKey>.navigateFromMain(key: NavKey) {
    if (lastOrNull() == Main) add(key)
}
