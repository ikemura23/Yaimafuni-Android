package com.ikmr.banbara23.yaeyama_liner_checker.front.top

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ikmr.banbara23.yaeyama_liner_checker.R
import com.ikmr.banbara23.yaeyama_liner_checker.common.Constants
import com.ikmr.banbara23.yaeyama_liner_checker.core.BaseFragment
import com.ikmr.banbara23.yaeyama_liner_checker.databinding.TopFragmentBinding
import com.ikmr.banbara23.yaeyama_liner_checker.front.status.list.StatusListTabActivity
import com.ikmr.banbara23.yaeyama_liner_checker.model.Company
import com.ikmr.banbara23.yaeyama_liner_checker.ui.portlisttab.PortListTabActivity
import timber.log.Timber

class TopFragment : Fragment(), TopView {

    private lateinit var binding: TopFragmentBinding
    private var topViewModel = TopViewModel()
    private lateinit var topPresenter: TopPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        topPresenter = TopPresenter(this, topViewModel)
        topPresenter.attachView(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.top_fragment, container, false)
        binding.presenter = topPresenter
        binding.viewModel = topViewModel
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        topPresenter.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        topPresenter.detachView()
        binding.unbind()
    }

    /**
     * 天気クリック
     */
    override fun navigateToWeather() {
        Timber.d("navigateToWeather")
        findNavController().navigate(R.id.action_topFragment_to_weatherFragment)
    }

    override fun navigateToTyphoon() {
        Timber.d("navigateToTyphoon")
        findNavController().navigate(R.id.action_topFragment_to_typhoonListFragment)
    }

    /**
     * 会社別ステータス クリック
     */
    override fun navigateToCompanyStatusList(company: Company) {
        Log.d(TAG, "navigateToCompanyStatusList:" + company.getName())
        val intent = Intent(activity, StatusListTabActivity::class.java)
        intent.putExtra(StatusListTabActivity::class.java.canonicalName, company)
        startActivity(intent)
    }

    /**
     * 港別ステータス クリック
     */
    override fun navigateToPortStatusList(portName: String, portCode: String) {
        Log.d(TAG, "navigateToPortStatusList:$portName")
        val intent = Intent(activity, PortListTabActivity::class.java)
        intent.putExtra(Constants.BUNDLE_KEY_PORT_NAME, portName)
        intent.putExtra(Constants.BUNDLE_KEY_PORT_CODE, portCode)
        startActivity(intent)
    }

    override fun getContext() = requireActivity()

    companion object {
        private val TAG = BaseFragment::class.java.simpleName

        fun newInstance(): Fragment {
            return TopFragment()
        }
    }
}
