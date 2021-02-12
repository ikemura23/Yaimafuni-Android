package com.ikmr.banbara23.yaeyama_liner_checker.ui.portlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ikmr.banbara23.yaeyama_liner_checker.R
import com.ikmr.banbara23.yaeyama_liner_checker.databinding.FragmentPortListTabBinding
import com.ikmr.banbara23.yaeyama_liner_checker.ui.portlisttab.PortPagerAdapter

/**
 * 港別の運行一覧
 */
class PortListTabFragment : Fragment() {
    private lateinit var binding: FragmentPortListTabBinding
    private val portCode: String by lazy {
        PortListTabFragmentArgs.fromBundle(requireArguments()).portCode
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_port_list_tab, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // binding.title = portName
        binding.includeTitleBar.titleBar.setNavigationOnClickListener { findNavController().navigateUp() }
        setupTab()
    }

    /**
     * タブの生成
     */
    private fun setupTab() {
        // 波照間航路は八重山観光フェリーがないので、八重山観光フェリータブを消して安栄タブのみにする
        if (portCode == "hateruma") {
            binding.tabLayout.removeTabAt(1)
        }
        binding.portViewPager.adapter =
            PortPagerAdapter(
                childFragmentManager,
                binding.tabLayout.tabCount,
                portCode
            )
        binding.tabLayout.setupWithViewPager(binding.portViewPager)
    }
}
