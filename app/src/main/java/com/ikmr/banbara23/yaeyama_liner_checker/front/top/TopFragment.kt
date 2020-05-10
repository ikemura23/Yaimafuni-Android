package com.ikmr.banbara23.yaeyama_liner_checker.front.top

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ikmr.banbara23.yaeyama_liner_checker.R
import com.ikmr.banbara23.yaeyama_liner_checker.databinding.TopFragmentBinding
import com.ikmr.banbara23.yaeyama_liner_checker.model.Company
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
     * 天気に遷移
     */
    override fun navigateToWeather() {
        Timber.d("navigateToWeather")
        findNavController().navigate(R.id.action_topFragment_to_weatherFragment)
    }

    /**
     *　台風に遷移
     */
    override fun navigateToTyphoon() {
        Timber.d("navigateToTyphoon")
        findNavController().navigate(R.id.action_topFragment_to_typhoonListFragment)
    }

    /**
     * 会社別ステータス クリック
     */
    override fun navigateToCompanyStatusList(company: Company) {
        Timber.d("navigateToCompanyStatusList:%s", company.getName())
        TopFragmentDirections.actionTopFragmentToStatusListTabFragment(company).let {
            findNavController().navigate(it)
        }
    }

    /**
     * 港別ステータス クリック
     */
    override fun navigateToPortStatusList(portName: String, portCode: String) {
        Timber.d("navigateToPortStatusList:$portName $portCode")
        findNavController().navigate(
            TopFragmentDirections.actionTopFragmentToPortListTabFragment(portName, portCode)
        )
        // val intent = Intent(activity, PortListTabActivity::class.java)
        // intent.putExtra(Constants.BUNDLE_KEY_PORT_NAME, portName)
        // intent.putExtra(Constants.BUNDLE_KEY_PORT_CODE, portCode)
        // startActivity(intent)
    }

    override fun getContext() = requireActivity()
}
