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
import com.ikmr.banbara23.yaeyama_liner_checker.ext.observeEvent
import com.ikmr.banbara23.yaeyama_liner_checker.ext.viewBinding
import kotlinx.coroutines.flow.collect
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
                                // TODO: 詳細へ画面遷移
                            }
                        }
                    }
                    is UiState.Error -> Timber.e(state.error)
                }
            }
        }
        viewModel.nav.observeEvent(viewLifecycleOwner, this::onNavigate)
    }

    /**
     * 画面遷移
     */
    private fun onNavigate(nav: DashBoardViewModelImpl.Nav) {
        Timber.d("navigate: $nav")
        when (nav) {
            is DashBoardViewModelImpl.Nav.GoDetail -> {
                DashBoardFragmentDirections.actionDashBoardFragmentToPortStatusDetailActivity(
                    portName = nav.ports.anei.portName,
                    portCode = nav.ports.anei.portCode,
                ).let { directions ->
                    findNavController().navigate(directions)
                }
            }
        }
    }
}
