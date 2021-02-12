package com.ikmr.banbara23.yaeyama_liner_checker.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ikmr.banbara23.yaeyama_liner_checker.databinding.DashBoardFragmentBinding
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
        viewModel.uiState.observe(viewLifecycleOwner) {
            Timber.d("$it")
            binding.topPort = it
        }
        viewModel.fetchTopPortStatus()
    }

    companion object {
        fun newInstance() = DashBoardFragment()
    }
}
