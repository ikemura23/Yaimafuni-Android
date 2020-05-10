package com.ikmr.banbara23.yaeyama_liner_checker.front.status.list

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.ikmr.banbara23.yaeyama_liner_checker.model.Company

/**
 * 会社一覧タブ PagerAdapter
 */
class PagerAdapter(
    fm: FragmentManager,
    private var tabCount: Int
) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        val company = when (position) {
            0 -> Company.ANEI
            1 -> Company.YKF
            else -> Company.ANEI
        }
        return StatusListTabFragment.NewInstance(company)
    }

    override fun getPageTitle(position: Int): String {
        return if (position == 0) "安栄観光" else "八重山観光\nフェリー"
    }

    override fun getCount(): Int {
        return tabCount
    }
}
