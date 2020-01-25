package com.ikmr.banbara23.yaeyama_liner_checker.front.top

import android.os.Bundle

import com.ikmr.banbara23.yaeyama_liner_checker.R
import com.ikmr.banbara23.yaeyama_liner_checker.core.BaseActivity

class TopActivity : BaseActivity() {
    private val TAG = TopActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.top_activity)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, TopFragment.newInstance())
            .commit()
    }
}
