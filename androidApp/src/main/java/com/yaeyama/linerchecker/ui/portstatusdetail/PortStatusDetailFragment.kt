package com.yaeyama.linerchecker.ui.portstatusdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.yaeyama.linerchecker.ext.getSerialize
import com.yaeyama.linerchecker.ui.theme.YaimafuniAndroidTheme
import com.yaeyama_liner_checker.domain.statusdetail.Company
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

/**
 * 詳細フラグメント
 */
class PortStatusDetailFragment : Fragment() {

    private val viewModel by viewModel<PortStatusDetailViewModel>()

    /** パラメータ取得 会社 */
    private val company: Company
        get() = arguments?.getSerialize(BUNDLE_KEY_COMPANY, Company::class.java)
            ?: throw IllegalArgumentException("PortStatusDetailFragment の Arguments から Company が取得できません")

    /** 港コード */
    private val portCode: String
        get() = arguments?.getSerialize(BUNDLE_KEY_PORT_CODE, String::class.java)
            ?: throw IllegalArgumentException("PortStatusDetailFragment の Arguments から PortCode が取得できません")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return ComposeView(requireContext()).apply {
            setContent {
                YaimafuniAndroidTheme {
                    PortStatusDetailScreen(
                        company = company,
                        portCode = portCode,
                        viewModel = viewModel,
                    )
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let { Timber.d(arguments.toString()) }
    }

    companion object {
        const val BUNDLE_KEY_COMPANY = "BUNDLE_KEY_COMPANY"
        const val BUNDLE_KEY_PORT_CODE = "BUNDLE_KEY_PORT_CODE"

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
