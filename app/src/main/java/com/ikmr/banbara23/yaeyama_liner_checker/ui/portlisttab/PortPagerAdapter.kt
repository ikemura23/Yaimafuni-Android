package com.ikmr.banbara23.yaeyama_liner_checker.ui.portlisttab

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.ikmr.banbara23.yaeyama_liner_checker.common.Constants
import com.ikmr.banbara23.yaeyama_liner_checker.model.Company
import com.ikmr.banbara23.yaeyama_liner_checker.ui.portstatusdetail.PortStatusDetailFragment

/**
 * 港別の運行一覧 PagerAdapter
 */
class PortPagerAdapter(
    fm: FragmentManager,
    private val tabCount: Int,
    private val portCode: String
) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        val company = when (position) {
            0 -> Company.ANEI
            1 -> Company.YKF
            else -> Company.ANEI
        }
        val bundle = Bundle().apply {
            putString(Constants.BUNDLE_KEY_PORT_CODE, portCode)
            putSerializable(Constants.BUNDLE_KEY_COMPANY, company)
        }

        return PortStatusDetailFragment.newInstance(bundle)
    }

    override fun getPageTitle(position: Int): String {
        return if (position == 0) "安栄観光" else "八重山観光\nフェリー"
    }

    override fun getCount(): Int {
        return tabCount
    }
}
