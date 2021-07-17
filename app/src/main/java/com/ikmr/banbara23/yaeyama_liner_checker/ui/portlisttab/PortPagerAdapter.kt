package com.ikmr.banbara23.yaeyama_liner_checker.ui.portlisttab

import android.content.Context
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.ikemura.shared.model.statusdetail.Company
import com.ikmr.banbara23.yaeyama_liner_checker.R
import com.ikmr.banbara23.yaeyama_liner_checker.common.Constants
import com.ikmr.banbara23.yaeyama_liner_checker.ui.portstatusdetail.PortStatusDetailFragment

/**
 * TODO: このクラスは使わなくなったので削除対象
 *
 * 港別の運行一覧 PagerAdapter
 */
class PortPagerAdapter(
    val context: Context,
    fm: FragmentManager,
    private val tabCount: Int,
    private val portCode: String,
) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
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

    override fun getPageTitle(position: Int): String {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        return tabCount
    }
}

/**
 * タブ名
 */
private val TAB_TITLES = arrayOf(
    R.string.tab_annei,
    R.string.tab_ykf
)
