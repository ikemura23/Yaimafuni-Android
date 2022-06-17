package com.ikmr.banbara23.yaeyama_liner_checker.ui.portstatusdetail

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ikmr.banbara23.yaeyama_liner_checker.common.Constants
import com.yaeyama_liner_checker.domain.statusdetail.Company

class PortStatusPagerAdapter(
    fa: FragmentActivity,
    private val portCode: String,
) : FragmentStateAdapter(fa) {

    override fun getItemCount(): Int {
        // 波照間のみ安栄観光しかないのでタブ数は1、それ以外は安栄観光と八重山観光フェリーの2
        return if (portCode == "hateruma") 1 else 2
    }

    override fun createFragment(position: Int): Fragment {
        val company = when (position) {
            0 -> Company.ANEI
            1 -> Company.YKF
            else -> Company.ANEI
        }

        val bundle = bundleOf(
            Constants.BUNDLE_KEY_PORT_CODE to portCode,
            Constants.BUNDLE_KEY_COMPANY to company
        )

        return PortStatusDetailFragment.newInstance(bundle)
    }
}
