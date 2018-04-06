package com.ikmr.banbara23.yaeyama_liner_checker.front.status.port

import android.databinding.DataBindingUtil
import android.os.Bundle
import com.ikmr.banbara23.yaeyama_liner_checker.R
import com.ikmr.banbara23.yaeyama_liner_checker.common.Constants
import com.ikmr.banbara23.yaeyama_liner_checker.core.BaseActivity
import com.ikmr.banbara23.yaeyama_liner_checker.databinding.PortActivityBinding

/**
 * 港の会社別ステータス一覧画面
 */
class PortListActivity : BaseActivity() {
    private lateinit var binding: PortActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.port_activity)

        binding = DataBindingUtil.setContentView(this, R.layout.port_activity)
        binding.includeTitleBar?.titleBar?.setNavigationOnClickListener { finish() }
        initTab()
    }

    private fun initTab() {
        val portCode: String = intent.getStringExtra(Constants.BUNDLE_KEY_PORT_CODE)
        if (portCode.equals("hateruma")) {
            binding.tabLayout.removeTabAt(1)
        }
        binding.portViewPager.adapter =
                PortPagerAdapter(
                        supportFragmentManager,
                        binding.tabLayout.tabCount,
                        portCode)
        binding.tabLayout.setupWithViewPager(binding.portViewPager)
    }

}