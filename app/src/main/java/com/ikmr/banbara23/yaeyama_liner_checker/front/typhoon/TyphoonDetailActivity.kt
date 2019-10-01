package com.ikmr.banbara23.yaeyama_liner_checker.front.typhoon

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ikmr.banbara23.yaeyama_liner_checker.R

class TyphoonDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_typhoon_detail)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, TyphoonDetailFragment.newInstance(5))
            .commit()
    }
}
