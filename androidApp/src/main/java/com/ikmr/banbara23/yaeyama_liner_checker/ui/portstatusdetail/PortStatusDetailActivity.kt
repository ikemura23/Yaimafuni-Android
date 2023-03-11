package com.ikmr.banbara23.yaeyama_liner_checker.ui.portstatusdetail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.ikmr.banbara23.yaeyama_liner_checker.R
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
        binding.viewPager.adapter = PortStatusPagerAdapter(
            fa = this,
            portCode = portCode,
        )
        // タブ名
        val tabTitles = arrayOf(
            R.string.tab_annei,
            R.string.tab_ykf,
        )

        // https://developer.android.com/guide/navigation/navigation-swipe-view-2
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = getString(tabTitles[position])
        }.attach()
    }
}
