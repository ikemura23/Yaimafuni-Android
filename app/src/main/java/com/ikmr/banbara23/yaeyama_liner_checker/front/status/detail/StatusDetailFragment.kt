package com.ikmr.banbara23.yaeyama_liner_checker.front.status.detail

import android.content.Intent
import android.databinding.DataBindingUtil
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ikmr.banbara23.yaeyama_liner_checker.R
import com.ikmr.banbara23.yaeyama_liner_checker.common.Constants
import com.ikmr.banbara23.yaeyama_liner_checker.core.BaseFragment
import com.ikmr.banbara23.yaeyama_liner_checker.databinding.StatusDetailFragmentBinding
import com.ikmr.banbara23.yaeyama_liner_checker.model.Company
import com.ikmr.banbara23.yaeyama_liner_checker.utils.CustomTabUtil

/**
 * 安栄の詳細フラグメント
 */
class StatusDetailFragment : BaseFragment(), StatusDetailView {
    private lateinit var binding: StatusDetailFragmentBinding
    private var viewModel = StatusDetailViewModel()
    private var linerViewModel = LinerInfoViewModel()
    private var timeTableViewModel = TimeTableViewModel()
    private lateinit var presenter: StatusDetailPresenter

    /**
     * パラメータ取得 会社
     *
     * @return
     */
    private val argCompany: Company
        get() = arguments!!.getSerializable(Constants.BUNDLE_KEY_COMPANY) as Company

    /**
     * パラメータ取得 港コード
     *
     * @return
     */
    private val argPortCode: String?
        get() = arguments!!.getString(Constants.BUNDLE_KEY_PORT_CODE)

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.status_detail_fragment, container, false)

        presenter = StatusDetailPresenter(viewModel, linerViewModel, timeTableViewModel, argCompany, argPortCode!!)
        presenter.attachView(this)
        binding.statusViewModel = viewModel
        binding.linerInfoViewModel = linerViewModel
        binding.timeTableViewModel = timeTableViewModel
        binding.presenter = presenter

        return binding.root
    }

    override fun getContext() = requireActivity()

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }

    /**
     * 電話アプリを起動
     *
     * @param tel
     */
    override fun openTell(tel: String) {
        if (TextUtils.isEmpty(tel)) {
            return
        }
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("tel:$tel"))
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
    override fun openBrowser(url: String) {
        if (TextUtils.isEmpty(url)) {
            return
        }
        CustomTabUtil.start(activity, url)
    }

    companion object {
        private val TAG = StatusDetailFragment::class.java.simpleName

        /**
         * New Instance
         *
         * @param bundle
         * @return
         */
        fun newInstance(bundle: Bundle): StatusDetailFragment {
            val fragment = StatusDetailFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}
