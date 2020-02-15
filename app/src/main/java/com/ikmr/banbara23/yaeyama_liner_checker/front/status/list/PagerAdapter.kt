package com.ikmr.banbara23.yaeyama_liner_checker.front.status.list

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

import com.ikmr.banbara23.yaeyama_liner_checker.model.Company

/**
 * PagerAdapter
 */
class PagerAdapter(fm: androidx.fragment.app.FragmentManager, internal var mNumOfTabs: Int) :
    androidx.fragment.app.FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): androidx.fragment.app.Fragment {
        val company: Company?
        when (position) {
            0 -> company = Company.ANEI
            1 -> company = Company.YKF
            else -> company = null
        }
        return StatusListTabFragment.NewInstance(company)
    }

    override fun getCount(): Int {
        return mNumOfTabs
    }
}
