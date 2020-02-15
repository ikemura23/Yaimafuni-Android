package com.ikmr.banbara23.yaeyama_liner_checker.ui.portlisttab

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.ikmr.banbara23.yaeyama_liner_checker.common.Constants
import com.ikmr.banbara23.yaeyama_liner_checker.model.Company
import com.ikmr.banbara23.yaeyama_liner_checker.ui.portstatusdetail.PortStatusDetailFragment

/**
 * PagerAdapter
 */
class PortPagerAdapter(
    fm: FragmentManager,
    private val tabCount: Int,
    private val portCode: String
) : androidx.fragment.app.FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        val company: Company?
        when (position) {
            0 -> company = Company.ANEI
            1 -> company = Company.YKF
            else -> company = null
        }
        val bundle = Bundle()
        bundle.putString(Constants.BUNDLE_KEY_PORT_CODE, portCode)
        bundle.putSerializable(Constants.BUNDLE_KEY_COMPANY, company)
        return PortStatusDetailFragment.newInstance(bundle)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return if (position == 0) "安栄観光" else "八重山観光\nフェリー"
    }

    override fun getCount(): Int {
        return tabCount
    }
}
