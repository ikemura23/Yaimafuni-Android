package com.ikmr.banbara23.yaeyama_liner_checker.ui.portlisttab

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ikmr.banbara23.yaeyama_liner_checker.R
import com.ikmr.banbara23.yaeyama_liner_checker.common.Constants
import com.ikmr.banbara23.yaeyama_liner_checker.databinding.PortTabActivityBinding

/**
 * 港の会社別ステータス一覧画面
 */
class PortListTabActivity : AppCompatActivity() {
    private lateinit var binding: PortTabActivityBinding

    private val portCode: String
        get() = intent.getStringExtra(Constants.BUNDLE_KEY_PORT_CODE)

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
        // 波照間航路は八重山観光フェリーがないので、八重山観光フェリータブを消して安栄タブのみにする
        if (portCode == "hateruma") {
            binding.tabLayout.removeTabAt(1)
        }
        binding.portViewPager.adapter =
            PortPagerAdapter(
                supportFragmentManager,
                binding.tabLayout.tabCount,
                portCode
            )
        binding.tabLayout.setupWithViewPager(binding.portViewPager)
    }
}
