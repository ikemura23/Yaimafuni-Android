package com.ikmr.banbara23.yaeyama_liner_checker.ui.companylist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.ikmr.banbara23.yaeyama_liner_checker.R
import com.ikmr.banbara23.yaeyama_liner_checker.common.Constants
import com.ikmr.banbara23.yaeyama_liner_checker.databinding.CompanyListFragmentBinding
import com.ikmr.banbara23.yaeyama_liner_checker.model.Company

class CompanyListFragment : Fragment() {

    private lateinit var viewModel: CompanyListViewModel
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        epoxyController = CompanyListEpoxyController()
        binding.recyclerView.setController(epoxyController)
        viewModel = ViewModelProviders.of(this).get(CompanyListViewModel::class.java)
    }

    companion object {
        fun newInstance(company: Company) = CompanyListFragment().apply {
            arguments = bundleOf(Constants.BUNDLE_KEY_COMPANY to company)
        }
    }
}
