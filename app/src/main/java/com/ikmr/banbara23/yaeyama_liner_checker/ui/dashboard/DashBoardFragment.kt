package com.ikmr.banbara23.yaeyama_liner_checker.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ikmr.banbara23.yaeyama_liner_checker.databinding.DashBoardFragmentBinding
import com.ikmr.banbara23.yaeyama_liner_checker.model.Company
import com.ikmr.banbara23.yaeyama_liner_checker.ui.portstatusdetail.PortStatusDetailActivity
import timber.log.Timber

/**
 * トップに表示するステータスのダッシュボード画面
 */
class DashBoardFragment : Fragment() {
    private lateinit var binding: DashBoardFragmentBinding

    private val viewModel by viewModels<DashBoardViewModelImpl>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DashBoardFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        viewModel.uiState.observe(viewLifecycleOwner) {
            Timber.d("$it")
            binding.topPort = it
        }
        viewModel.fetchTopPortStatus()
        viewModel.nav.observe(viewLifecycleOwner, this::onNavigate)
    }

    /**
     * 画面遷移
     */
    private fun onNavigate(nav: DashBoardViewModelImpl.Nav) {
        Timber.d("navigate: $nav")
        when (nav) {
            is DashBoardViewModelImpl.Nav.GoDetail -> {

                // DashBoardFragmentDirections.actionDashBoardFragmentToPortStatusDetailActivity(
                //     company = Company.ANEI,
                //     portName = nav.ports.anei.portName,
                //     portCode = nav.ports.anei.portCode,
                // ).let { directions ->
                //     findNavController().navigate(directions)
                // }
                DashBoardFragmentDirections.actionDashBoardFragmentToPortStatusDetailActivity(
                    company = Company.ANEI,
                    portName = nav.ports.anei.portName,
                    portCode = nav.ports.anei.portCode,
                ).let { directions ->
                    Timber.d(directions.arguments.toString())
                    Intent(requireActivity(), PortStatusDetailActivity::class.java).apply {
                        putExtras(directions.arguments)
                    }
                }.let { intent ->
                    Timber.d(intent.extras.toString())
                    startActivity(intent)
                }
            }
        }
    }

    companion object {
        fun newInstance() = DashBoardFragment()
    }
}
