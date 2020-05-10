package com.ikmr.banbara23.yaeyama_liner_checker.ui.companylist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ikmr.banbara23.yaeyama_liner_checker.R
import com.ikmr.banbara23.yaeyama_liner_checker.databinding.FragmentCompanyListTabBinding
import com.ikmr.banbara23.yaeyama_liner_checker.model.Company

class CompanyListTabFragment : Fragment() {
    private lateinit var binding: FragmentCompanyListTabBinding
    private val company: Company by lazy {
        CompanyListTabFragmentArgs.fromBundle(requireArguments()).company
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_company_list_tab, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.includeTitleBar.titleBar.setNavigationOnClickListener { findNavController().navigateUp() }
        setupTab()
    }

    /**
     * タブの生成
     */
    private fun setupTab() {
        binding.tabViewPager.adapter = CompanyTabPagerAdapter(
            childFragmentManager,
            binding.tabLayout.tabCount
        )
        binding.tabLayout.setupWithViewPager(binding.tabViewPager)
        // 初期選択タブの設定
        when (company) {
            Company.ANEI -> binding.tabViewPager.currentItem = 0
            Company.YKF -> binding.tabViewPager.currentItem = 1
        }
    }
}
