package com.ikmr.banbara23.yaeyama_liner_checker.ui.dashboard

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ikemura.shared.repository.UiState
import com.ikmr.banbara23.yaeyama_liner_checker.R
import com.ikmr.banbara23.yaeyama_liner_checker.databinding.DashBoardFragmentBinding
import com.ikmr.banbara23.yaeyama_liner_checker.ext.viewBinding
import timber.log.Timber

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
            viewModel.fetchTopStatuses().collect { state ->
                when (state) {
                    is UiState.Success -> {
                        binding.DashBoardComposeView.apply {
                            ports = state.data
                            onRowClick = { port ->
                                Timber.d("clicked status: $port")
                                navigateToStatusDetail(
                                    portCode = port.anei.portCode,
                                    portName = port.anei.portName
                                )
                            }
                        }
                    }
                    is UiState.Error -> Timber.e(state.error)
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
