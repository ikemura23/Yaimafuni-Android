package com.ikmr.banbara23.yaeyama_liner_checker.ui.portstatusdetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.analytics.FirebaseAnalytics
import com.ikmr.banbara23.yaeyama_liner_checker.R
import com.ikmr.banbara23.yaeyama_liner_checker.common.Constants
import com.ikmr.banbara23.yaeyama_liner_checker.common.CustomLinearLayoutManager
import com.ikmr.banbara23.yaeyama_liner_checker.databinding.StatusDetailFragmentBinding
import com.ikmr.banbara23.yaeyama_liner_checker.model.Company
import com.ikmr.banbara23.yaeyama_liner_checker.utils.CustomTabUtil

/**
 * 詳細フラグメント
 */
class PortStatusDetailFragment : Fragment() {
    private lateinit var binding: StatusDetailFragmentBinding
    private val viewModel: PortStatusDetailViewModel by lazy {
        ViewModelProviders.of(this).get(PortStatusDetailViewModel::class.java)
    }
    private val firebaseAnalytics: FirebaseAnalytics by lazy { FirebaseAnalytics.getInstance(requireActivity()) }

    /** パラメータ取得 会社 */
    private val company: Company
        get() = arguments?.getSerializable(Constants.BUNDLE_KEY_COMPANY) as Company

    /** 港コード */
    private val portCode: String
        get() = arguments?.getString(Constants.BUNDLE_KEY_PORT_CODE) ?: ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.status_detail_fragment, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    private fun setupViews() {
        binding.let {
            // 時刻表
            it.timeTable.list.layoutManager = CustomLinearLayoutManager(requireContext())
            it.timeTable.list.adapter = PortStatusDetailAdapter(viewLifecycleOwner, viewModel.timeTable)
            // Webで見る
            it.action.web.setOnClickListener { viewModel.startWeb() }
            // 電話する
            it.action.tell.setOnClickListener { viewModel.startTel() }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        viewModel.event.observe(viewLifecycleOwner, Observer { nav ->
            when (nav) {
                is PortStatusDetailViewModel.Nav.Error -> {
                    // todo: エラーメッセージ
                }
                is PortStatusDetailViewModel.Nav.Web -> {
                    openBrowser(nav.url)
                }
                is PortStatusDetailViewModel.Nav.Tell -> {
                    openTell(nav.tellNo)
                }
            }
        })
        viewModel.load(company, portCode)
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
            Uri.parse("tel:$tel")
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
        CustomTabUtil.start(activity, url)
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
