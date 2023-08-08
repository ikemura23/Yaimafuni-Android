package com.yaeyama.linerchecker.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.yaeyama.linerchecker.ui.theme.YaimafuniAndroidTheme

/**
 * トップに表示するステータスのダッシュボード画面
 */
class DashBoardFragment : Fragment() {

    private val viewModel by viewModels<DashBoardViewModel>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return ComposeView(requireContext()).apply {
            setContent {
                YaimafuniAndroidTheme {
                    DashBoardScreen(
                        viewModel = viewModel,
                        onRowClick = { port -> // TODO: portCodeとportNameだけ渡せばいい
                            navigateToStatusDetail(
                                portCode = port.anei.portCode,
                                portName = port.anei.portName,
                            )
                        },
                    )
                }
            }
        }
    }

    /**
     * 画面遷移
     */
    private fun navigateToStatusDetail(portName: String, portCode: String) {
        DashBoardFragmentDirections.actionDashBoardFragmentToPortStatusDetailActivity(
            portName = portName,
            portCode = portCode,
        ).let { directions ->
            findNavController().navigate(directions)
        }
    }
}
