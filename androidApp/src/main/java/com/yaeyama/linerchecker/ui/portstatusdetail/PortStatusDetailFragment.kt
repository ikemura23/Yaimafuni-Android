package com.yaeyama.linerchecker.ui.portstatusdetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.firebase.analytics.FirebaseAnalytics
import com.ikmr.banbara23.yaeyama_liner_checker.R
import com.ikmr.banbara23.yaeyama_liner_checker.databinding.StatusDetailFragmentBinding
import com.yaeyama.linerchecker.common.Constants.BUNDLE_KEY_COMPANY
import com.yaeyama.linerchecker.common.Constants.BUNDLE_KEY_PORT_CODE
import com.yaeyama.linerchecker.ext.viewBinding
import com.yaeyama.linerchecker.repository.UiState
import com.yaeyama.linerchecker.utils.CustomTabUtil
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
    private val firebaseAnalytics: FirebaseAnalytics by lazy {
        FirebaseAnalytics.getInstance(
            requireActivity(),
        )
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

    /**
     * 電話アプリを起動
     *
     * @param tel
     */
    private fun openTell(tel: String) {
        // アナリティクス イベント送信
        val bundle = Bundle().apply {
            putString(FirebaseAnalytics.Param.CONTENT_TYPE, "tel")
        }
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)

        if (TextUtils.isEmpty(tel)) {
            return
        }
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("tel:$tel"),
        )
        try {
            startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 外部ブラウザを起動
     *
     * @param url
     */
    private fun openBrowser(url: String) {
        // アナリティクス イベント送信
        val bundle = Bundle().apply {
            putString(FirebaseAnalytics.Param.CONTENT_TYPE, "web")
        }
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)

        if (TextUtils.isEmpty(url)) {
            return
        }
        CustomTabUtil.start(requireActivity(), url)
    }

//    override fun onWebClicked(url: String) {
//        openBrowser(url)
//    }
//
//    override fun onTelClicked(tel: String) {
//        openTell(tel)
//    }

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
