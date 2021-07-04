package com.ikmr.banbara23.yaeyama_liner_checker.ui.portlist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ikmr.banbara23.yaeyama_liner_checker.R
import com.ikmr.banbara23.yaeyama_liner_checker.databinding.FragmentPortListTabBinding
import com.ikmr.banbara23.yaeyama_liner_checker.ext.viewBinding
import com.ikmr.banbara23.yaeyama_liner_checker.ui.portlisttab.PortPagerAdapter

/**
 * 港別の運行一覧
 */
class PortListTabFragment : Fragment(R.layout.fragment_port_list_tab) {
    private val binding: FragmentPortListTabBinding by viewBinding()
    private val portCode: String by lazy {
        PortListTabFragmentArgs.fromBundle(requireArguments()).portCode
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTitle()
        setupTab()
    }

    /**
     * タイトル設定
     */
    private fun setupTitle() {
        binding.title = PortListTabFragmentArgs.fromBundle(requireArguments()).portName
        binding.includeTitleBar.titleBar.setNavigationOnClickListener { findNavController().navigateUp() }
    }

    /**
     * タブの生成
     */
    private fun setupTab() {
        // 波照間航路は八重山観光フェリーがないので、八重山観光フェリータブを消して安栄タブのみにする
        if (portCode == "hateruma") {
            binding.tabLayout.removeTabAt(1)
        }
        PortListTabFragmentArgs
        binding.portViewPager.adapter =
            PortPagerAdapter(
                requireContext(),
                childFragmentManager,
                binding.tabLayout.tabCount,
                portCode,
            )
        binding.tabLayout.setupWithViewPager(binding.portViewPager)
    }
}
