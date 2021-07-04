package com.ikmr.banbara23.yaeyama_liner_checker.ui.companydetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.analytics.FirebaseAnalytics
import com.ikmr.banbara23.yaeyama_liner_checker.R
import com.ikmr.banbara23.yaeyama_liner_checker.databinding.CompanyDetailFragmentBinding
import com.ikmr.banbara23.yaeyama_liner_checker.ext.viewBinding
import com.ikmr.banbara23.yaeyama_liner_checker.model.Company
import com.ikmr.banbara23.yaeyama_liner_checker.ui.portstatusdetail.PortStatusDetailViewModel
import com.ikmr.banbara23.yaeyama_liner_checker.ui.portstatusdetail.StatusDetailEpoxyController
import com.ikmr.banbara23.yaeyama_liner_checker.utils.CustomTabUtil

class CompanyDetailFragment : Fragment(R.layout.company_detail_fragment),
    StatusDetailEpoxyController.StatusDetailClickListener {
    private val binding: CompanyDetailFragmentBinding by viewBinding()
    private val firebaseAnalytics: FirebaseAnalytics by lazy { FirebaseAnalytics.getInstance(requireActivity()) }
    private lateinit var controller: StatusDetailEpoxyController
    private val viewModel: PortStatusDetailViewModel by lazy {
        ViewModelProvider(this).get(PortStatusDetailViewModel::class.java)
    }

    /** パラメータ取得 会社 */
    private val company: Company
        get() = CompanyDetailFragmentArgs.fromBundle(requireArguments()).company

    /** 港コード */
    private val portCode: String
        get() = CompanyDetailFragmentArgs.fromBundle(requireArguments()).portCode

    private fun setupViews() {
        controller = StatusDetailEpoxyController(this)
        binding.listView.adapter = controller.adapter
        binding.title = company.getName()
        binding.includeTitleBar.titleBar.setNavigationOnClickListener { findNavController().navigateUp() }
        viewModel.statusDetailRoot.observe(viewLifecycleOwner, Observer {
            controller.setData(it)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        viewModel.load(company, portCode)
    }

    override fun onDestroyView() {
        viewModel.dispose()
        super.onDestroyView()
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
        CustomTabUtil.start(requireActivity(), url)
    }

    override fun onWebClicked(url: String) {
        openBrowser(url)
    }

    override fun onTelClicked(tel: String) {
        openTell(tel)
    }
}
