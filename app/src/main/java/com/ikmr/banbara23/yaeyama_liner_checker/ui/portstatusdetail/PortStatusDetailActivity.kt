package com.ikmr.banbara23.yaeyama_liner_checker.ui.portstatusdetail

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.ikmr.banbara23.yaeyama_liner_checker.R
import com.ikmr.banbara23.yaeyama_liner_checker.common.Constants
import com.ikmr.banbara23.yaeyama_liner_checker.databinding.StatusDetailActivityBinding
import com.ikmr.banbara23.yaeyama_liner_checker.model.Company

/**
 * ステータス詳細のActivity
 */
class PortStatusDetailActivity : AppCompatActivity() {

    private val company: Company
        get() = intent?.getSerializableExtra(Constants.BUNDLE_KEY_COMPANY) as Company

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.status_detail_activity)
        val binding = DataBindingUtil.setContentView<StatusDetailActivityBinding>(this, R.layout.status_detail_activity)
        binding.includeTitleBar.titleBar.setNavigationOnClickListener { finish() }
        binding.title = company.getName()
        supportActionBar?.run { setDisplayHomeAsUpEnabled(true) }

        supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, PortStatusDetailFragment.newInstance(intent.extras!!))
                .commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}

