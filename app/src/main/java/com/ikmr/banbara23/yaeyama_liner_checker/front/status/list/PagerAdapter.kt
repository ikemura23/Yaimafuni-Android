package com.ikmr.banbara23.yaeyama_liner_checker.front.status.list

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

import com.ikmr.banbara23.yaeyama_liner_checker.model.Company

/**
 * PagerAdapter
 */
class PagerAdapter(fm: FragmentManager, internal var mNumOfTabs: Int) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
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
