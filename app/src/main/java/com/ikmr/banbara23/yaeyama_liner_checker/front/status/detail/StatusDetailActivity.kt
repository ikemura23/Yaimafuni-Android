package com.ikmr.banbara23.yaeyama_liner_checker.front.status.detail

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.MenuItem
import android.view.View

import com.ikmr.banbara23.yaeyama_liner_checker.R
import com.ikmr.banbara23.yaeyama_liner_checker.common.Constants
import com.ikmr.banbara23.yaeyama_liner_checker.core.BaseActivity
import com.ikmr.banbara23.yaeyama_liner_checker.databinding.StatusDetailActivityBinding
import com.ikmr.banbara23.yaeyama_liner_checker.model.Company

/**
 * ステータス詳細のActivity
 */
class StatusDetailActivity : BaseActivity() {
    private val TAG = StatusDetailActivity::class.java.simpleName
    private lateinit var binding: StatusDetailActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.status_detail_activity)
        binding = DataBindingUtil.setContentView(this, R.layout.status_detail_activity)
        binding.includeTitleBar!!.titleBar.setNavigationOnClickListener { finish() }
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }

        setScreenTitle()

        supportFragmentManager.beginTransaction()
                .replace(R.id.container, StatusDetailFragment.NewInstance(intent.extras!!))
                .commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * タイトルバーの設定
     */
    private fun setScreenTitle() {
        val company = intent.getSerializableExtra(Constants.BUNDLE_KEY_COMPANY) as Company
        binding.title = company.getName()
    }
}
