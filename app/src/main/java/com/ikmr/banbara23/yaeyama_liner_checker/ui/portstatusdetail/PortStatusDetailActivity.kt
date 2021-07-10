package com.ikmr.banbara23.yaeyama_liner_checker.ui.portstatusdetail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ikmr.banbara23.yaeyama_liner_checker.databinding.PortStatusDetailActivityBinding

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
        setupTitle()
        setupTabLayout()
    }

    /**
     * タイトル設定
     */
    private fun setupTitle() {
        binding.includeTitleBar.title = portName
        binding.includeTitleBar.titleBar.setNavigationOnClickListener {
            finish()
        }
    }

    /**
     * タブ設定
     */
    private fun setupTabLayout() {
        val pagerAdapter = PortStatusPagerAdapter(
            this,
            portCode,
        )

        val viewPager = binding.viewPager
        viewPager.adapter = pagerAdapter
        // val tabs: TabLayout = binding.tabs
        // tabs.setupWithViewPager(viewPager)
    }
}
