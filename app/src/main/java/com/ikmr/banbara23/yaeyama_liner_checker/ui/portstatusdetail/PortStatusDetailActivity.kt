package com.ikmr.banbara23.yaeyama_liner_checker.ui.portstatusdetail

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
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
        DataBindingUtil.setContentView<StatusDetailActivityBinding>(this, R.layout.status_detail_activity).let {
            it.includeTitleBar.titleBar.setNavigationOnClickListener { finish() }
            it.title = company.getName()
        }

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

