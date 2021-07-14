package com.ikmr.banbara23.yaeyama_liner_checker.ui.dashboard

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ikmr.banbara23.yaeyama_liner_checker.R
import com.ikmr.banbara23.yaeyama_liner_checker.databinding.DashBoardFragmentBinding
import com.ikmr.banbara23.yaeyama_liner_checker.ext.observeEvent
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
        viewModel.uiState.observe(viewLifecycleOwner) {
            Timber.d("$it")
            binding.topPort = it
        }
        viewModel.fetchTopPortStatus()
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
