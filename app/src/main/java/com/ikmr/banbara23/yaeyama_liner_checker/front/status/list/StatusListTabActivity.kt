package com.ikmr.banbara23.yaeyama_liner_checker.front.status.list

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import com.ikmr.banbara23.yaeyama_liner_checker.R
import com.ikmr.banbara23.yaeyama_liner_checker.core.BaseActivity
import com.ikmr.banbara23.yaeyama_liner_checker.databinding.ActivityListTabBinding
import com.ikmr.banbara23.yaeyama_liner_checker.model.Company

/**
 * 一覧タブActivity
 */
class StatusListTabActivity : BaseActivity() {

    internal var tabLayout: TabLayout? = null

    internal var viewPager: ViewPager? = null

    /**
     * 前回開いていたタブ番号を取得
     *
     * @return タブ番号を取得
     */
    private val currentPosition: Int
        get() {
            val company = intent.getSerializableExtra(StatusListTabActivity::class.java.canonicalName) as Company
            return when (company) {
                Company.ANEI -> TAB_FIRST
                Company.YKF -> TAB_SECOND
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_tab)
        val binding = DataBindingUtil.setContentView<ActivityListTabBinding>(this, R.layout.activity_list_tab)
        binding.includeTitleBar.titleBar.setNavigationOnClickListener { finish() }
        tabLayout = binding.activityListTabLayout
        viewPager = binding.activityListTabViewPager

        createTab(currentPosition)
    }

    /**
     * タブの作成
     *
     * @param position
     */
    private fun createTab(position: Int) {
        if (tabLayout == null) {
            return
        }
        if (viewPager == null) {
            return
        }

        tabLayout!!.removeAllTabs()
        tabLayout!!.addTab(tabLayout!!.newTab().setText(getString(R.string.company_tab_name_annei)))
        tabLayout!!.addTab(tabLayout!!.newTab().setText(getString(R.string.company_tab_name_ykf)))
        tabLayout!!.tabGravity = TabLayout.GRAVITY_FILL
        val adapter = PagerAdapter(supportFragmentManager, tabLayout!!.tabCount)
        viewPager!!.adapter = adapter
        viewPager!!.currentItem = position
        viewPager!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager!!.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
            }
        })
    }

    companion object {

        private val TAG = StatusListTabActivity::class.java.simpleName
        private val TAB_FIRST = 0
        private val TAB_SECOND = 1
    }
}
