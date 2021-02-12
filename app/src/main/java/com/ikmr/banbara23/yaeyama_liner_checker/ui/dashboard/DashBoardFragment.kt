package com.ikmr.banbara23.yaeyama_liner_checker.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ikmr.banbara23.yaeyama_liner_checker.databinding.DashBoardFragmentBinding

/**
 * トップに表示するステータスのダッシュボード画面
 */
class DashBoardFragment : Fragment() {
    private lateinit var binding: DashBoardFragmentBinding
    private lateinit var viewModel: DashBoardViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DashBoardFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(DashBoardViewModelImpl::class.java)
    }

    companion object {
        fun newInstance() = DashBoardFragment()
    }
}
