package com.yaeyama.linerchecker.ui.dashboard

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ikmr.banbara23.yaeyama_liner_checker.R
import com.ikmr.banbara23.yaeyama_liner_checker.databinding.DashBoardFragmentBinding
import com.yaeyama.linerchecker.ext.viewBinding
import com.yaeyama.linerchecker.ui.theme.YaimafuniAndroidTheme

/**
 * トップに表示するステータスのダッシュボード画面
 */
class DashBoardFragment : Fragment(R.layout.dash_board_fragment) {
    private val binding: DashBoardFragmentBinding by viewBinding()

    private val viewModel by viewModels<DashBoardViewModelImpl>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            binding.composeView.setContent {
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
        com.yaeyama.linerchecker.ui.dashboard.DashBoardFragmentDirections.actionDashBoardFragmentToPortStatusDetailActivity(
            portName = portName,
            portCode = portCode,
        ).let { directions ->
            findNavController().navigate(directions)
        }
    }
}
