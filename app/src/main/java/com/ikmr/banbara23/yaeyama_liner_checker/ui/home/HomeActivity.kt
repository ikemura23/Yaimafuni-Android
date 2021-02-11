package com.ikmr.banbara23.yaeyama_liner_checker.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ikmr.banbara23.yaeyama_liner_checker.R

/**
 * ホーム画面、Bottom NavigationのあるActivity
 */
class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        // val navController = findNavController(R.id.nav_host_fragment) <= ビルドは成功するが起動するとエラーとなる、↓で解決
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navView.setupWithNavController(navHostFragment.navController)
    }
}
