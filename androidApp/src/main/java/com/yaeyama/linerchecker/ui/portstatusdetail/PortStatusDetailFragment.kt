package com.yaeyama.linerchecker.ui.portstatusdetail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.ikmr.banbara23.yaeyama_liner_checker.R
import com.ikmr.banbara23.yaeyama_liner_checker.databinding.StatusDetailFragmentBinding
import com.yaeyama.linerchecker.common.Constants.BUNDLE_KEY_COMPANY
import com.yaeyama.linerchecker.common.Constants.BUNDLE_KEY_PORT_CODE
import com.yaeyama.linerchecker.ext.viewBinding
import com.yaeyama.linerchecker.repository.UiState
import com.yaeyama_liner_checker.domain.statusdetail.Company
import com.yaeyama_liner_checker.domain.statusdetail.StatusDetailResult
import timber.log.Timber

/**
 * 詳細フラグメント
 */
class PortStatusDetailFragment : Fragment(R.layout.status_detail_fragment) {
    private val binding: StatusDetailFragmentBinding by viewBinding()
    private val viewModel: PortStatusDetailViewModel by lazy {
        ViewModelProvider(this).get(PortStatusDetailViewModel::class.java)
    }

    /** パラメータ取得 会社 */
    private val company: Company
        get() = arguments?.getSerializable(BUNDLE_KEY_COMPANY) as? Company ?: Company.ANEI

    /** 港コード */
    private val portCode: String
        get() = arguments?.getString(BUNDLE_KEY_PORT_CODE)
            ?: throw IllegalArgumentException("portCodeがありません")

    private fun setupViews() {
        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            viewModel.getStatusDetail(company, portCode).collect { uiState ->
                when (uiState) {
                    is UiState.Success<StatusDetailResult> -> {
                        binding.PortMainStatusComposeView.portStatus = uiState.data.portStatus
                        binding.TimeTableListComposeView.timeTable = uiState.data.timeTable
                    }

                    else -> {
                        // エラー表示はまだ未定
                    }
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        arguments?.let { Timber.d(arguments.toString()) }
    }

    companion object {
        /**
         * New Instance
         *
         * @param bundle
         * @return
         */
        fun newInstance(bundle: Bundle): PortStatusDetailFragment {
            val fragment = PortStatusDetailFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}
