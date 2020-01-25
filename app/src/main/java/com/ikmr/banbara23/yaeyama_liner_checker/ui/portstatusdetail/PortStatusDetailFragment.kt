package com.ikmr.banbara23.yaeyama_liner_checker.ui.portstatusdetail

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    /**
     * パラメータ取得 会社
     *
     * @return
     */
    private val company: Company
        get() = arguments!!.getSerializable(Constants.BUNDLE_KEY_COMPANY) as Company

    /**
     * パラメータ取得 港コード
     *
     * @return
     */
    private val portCode: String
        get() = arguments!!.getString(Constants.BUNDLE_KEY_PORT_CODE) ?: ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.status_detail_fragment, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.timeTable.list.layoutManager = CustomLinearLayoutManager(requireContext())
        binding.timeTable.list.adapter = PortStatusDetailAdapter(viewLifecycleOwner, viewModel.timeTable)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        if (TextUtils.isEmpty(url)) {
            return
        }
        CustomTabUtil.start(activity, url)
    }

    companion object {
        private val TAG = PortStatusDetailFragment::class.java.simpleName

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
