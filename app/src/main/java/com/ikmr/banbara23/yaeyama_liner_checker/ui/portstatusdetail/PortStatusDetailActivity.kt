package com.ikmr.banbara23.yaeyama_liner_checker.ui.portstatusdetail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.ikmr.banbara23.yaeyama_liner_checker.databinding.PortStatusDetailActivityBinding
import com.ikmr.banbara23.yaeyama_liner_checker.ui.portlisttab.PortPagerAdapter

/**
 * ステータス詳細のActivity
 */
class PortStatusDetailActivity : AppCompatActivity() {

    private lateinit var binding: PortStatusDetailActivityBinding

    private val portCode: String by lazy {
        PortStatusDetailActivityArgs.fromBundle(intent.extras!!).portCode
    }

    private val portName: String by lazy {
        PortStatusDetailActivityArgs.fromBundle(intent.extras!!).portName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = PortStatusDetailActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupTabLayout()
    }

    /**
     * タブ設定
     */
    private fun setupTabLayout() {
        val portPagerAdapter = PortPagerAdapter(
            this,
            supportFragmentManager,
            binding.tabs.tabCount,
            portCode,
        )

        val viewPager: ViewPager = binding.viewPager
        viewPager.adapter = portPagerAdapter
        val tabs: TabLayout = binding.tabs
        tabs.setupWithViewPager(viewPager)
    }
}
