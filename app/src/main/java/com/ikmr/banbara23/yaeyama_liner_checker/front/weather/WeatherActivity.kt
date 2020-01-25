package com.ikmr.banbara23.yaeyama_liner_checker.front.weather

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ikmr.banbara23.yaeyama_liner_checker.R
import com.ikmr.banbara23.yaeyama_liner_checker.databinding.WeatherActivityBinding

class WeatherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.weather_activity)
        val binding = DataBindingUtil.setContentView<WeatherActivityBinding>(this, R.layout.weather_activity)
        binding.includeTitleBar.titleBar.setNavigationOnClickListener { finish() }
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, WeatherFragment.newInstance())
            .commit()
    }
}
