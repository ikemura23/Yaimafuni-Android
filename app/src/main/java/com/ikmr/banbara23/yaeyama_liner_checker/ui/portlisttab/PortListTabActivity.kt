package com.ikmr.banbara23.yaeyama_liner_checker.ui.portlisttab

import android.databinding.DataBindingUtil
import android.os.Bundle
import com.ikmr.banbara23.yaeyama_liner_checker.R
import com.ikmr.banbara23.yaeyama_liner_checker.common.Constants
import com.ikmr.banbara23.yaeyama_liner_checker.core.BaseActivity
import com.ikmr.banbara23.yaeyama_liner_checker.databinding.PortTabActivityBinding

/**
 * 港の会社別ステータス一覧画面
 */
class PortListTabActivity : BaseActivity() {
    private lateinit var binding: PortTabActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.port_tab_activity)
        binding.includeTitleBar?.titleBar?.setNavigationOnClickListener { finish() }
        initTitle()
        initTab()
    }

    private fun initTitle() {
        binding.title = intent.getStringExtra(Constants.BUNDLE_KEY_PORT_NAME)
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

    private fun getPortCode(): String {
        return intent.getStringExtra(Constants.BUNDLE_KEY_PORT_CODE)
    }
}
