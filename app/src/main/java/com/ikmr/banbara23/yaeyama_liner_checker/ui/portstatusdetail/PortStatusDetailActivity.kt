package com.ikmr.banbara23.yaeyama_liner_checker.ui.portstatusdetail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.ikmr.banbara23.yaeyama_liner_checker.databinding.PortStatusDetailActivityBinding
import com.ikmr.banbara23.yaeyama_liner_checker.ui.portstatusdetail.ui.main.SectionsPagerAdapter

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

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        tabs.setupWithViewPager(viewPager)
    }
}
