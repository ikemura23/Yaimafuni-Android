package com.ikmr.banbara23.yaeyama_liner_checker.ui.companylist

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.ikmr.banbara23.yaeyama_liner_checker.R
import com.ikmr.banbara23.yaeyama_liner_checker.common.Constants
import com.ikmr.banbara23.yaeyama_liner_checker.databinding.CompanyListFragmentBinding
import com.ikmr.banbara23.yaeyama_liner_checker.ext.viewBinding
import com.ikmr.banbara23.yaeyama_liner_checker.model.Company
import com.ikmr.banbara23.yaeyama_liner_checker.model.CompanyStatus
import com.ikmr.banbara23.yaeyama_liner_checker.model.PortStatus
import timber.log.Timber

class CompanyListFragment : Fragment(R.layout.company_list_fragment) {

    private val viewModel by viewModels<CompanyListViewModel>()
    private lateinit var epoxyController: CompanyListEpoxyController
    private val binding: CompanyListFragmentBinding by viewBinding()
    private val company: Company by lazy {
        requireArguments().getSerializable(Constants.BUNDLE_KEY_COMPANY) as Company
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
        // viewModel.load(company)
    }

    private fun onItemClicked(portStatus: PortStatus) {
        Timber.d(company.toString())
        Timber.d(portStatus.toString())
        val bundle = bundleOf(
            Constants.BUNDLE_KEY_COMPANY to company,
            Constants.BUNDLE_KEY_PORT_CODE to portStatus.portCode
        )

        findNavController().navigate(
            CompanyListTabFragmentDirections.actionCompanyListTabFragmentToCompanyDetailFragment(
                company = company,
                portCode = portStatus.portCode,
                portName = portStatus.portName
            )
        )
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
