package com.ikmr.banbara23.yaeyama_liner_checker.ui.companylist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.ikmr.banbara23.yaeyama_liner_checker.R
import com.ikmr.banbara23.yaeyama_liner_checker.common.Constants
import com.ikmr.banbara23.yaeyama_liner_checker.databinding.CompanyListFragmentBinding
import com.ikmr.banbara23.yaeyama_liner_checker.model.Company
import com.ikmr.banbara23.yaeyama_liner_checker.model.CompanyStatus
import com.ikmr.banbara23.yaeyama_liner_checker.model.PortStatus
import timber.log.Timber

class CompanyListFragment : Fragment() {

    private val viewModel by viewModels<CompanyListViewModel>()
    private lateinit var epoxyController: CompanyListEpoxyController
    private lateinit var binding: CompanyListFragmentBinding
    private val company: Company by lazy {
        requireArguments().getSerializable(Constants.BUNDLE_KEY_COMPANY) as Company
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.company_list_fragment, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Epoxy
        epoxyController = CompanyListEpoxyController()
        binding.recyclerView.setController(epoxyController)
        // ViewModel
        viewModel.companyStatus.observe(viewLifecycleOwner, Observer {
            val uiData = CompanyListUIData(
                comment = it.comment ?: "",
                updateTime = it.updateTime ?: "",
                list = convertToList(it),
                onClickHandler = ::onItemClicked
            )
            epoxyController.setData(uiData)
        })
        viewModel.load(company)
    }

    private fun onItemClicked(portStatus: PortStatus) {
        Timber.d(portStatus.toString())
    }

    companion object {
        fun newInstance(company: Company) = CompanyListFragment().apply {
            arguments = bundleOf(Constants.BUNDLE_KEY_COMPANY to company)
        }
    }

    /**
     * 運行情報を配列にする
     */
    private fun convertToList(companyStatus: CompanyStatus): List<PortStatus> {
        val list = arrayListOf<PortStatus>(
            companyStatus.taketomi,
            companyStatus.kohama,
            companyStatus.kuroshima,
            companyStatus.oohara,
            companyStatus.uehara
        )
        return list.also {
            companyStatus.hateruma?.let { hateruma ->
                it.add(hateruma)
            }
        }.toList()
    }
}

typealias OnClickHandler = (portStatus: PortStatus) -> Unit

data class CompanyListUIData(
    val comment: String,
    val updateTime: String,
    val list: List<PortStatus>,
    val onClickHandler: OnClickHandler
)
