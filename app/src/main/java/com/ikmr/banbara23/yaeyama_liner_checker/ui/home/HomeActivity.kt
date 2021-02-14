package com.ikmr.banbara23.yaeyama_liner_checker.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.tasks.Task
import com.ikmr.banbara23.yaeyama_liner_checker.R
import com.ikmr.banbara23.yaeyama_liner_checker.di.AppInjector
import timber.log.Timber

/**
 * ホーム画面、Bottom NavigationのあるActivity
 */
class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        // val navController = findNavController(R.id.nav_host_fragment) <= ビルドは成功するが起動するとエラーとなる、↓で解決
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navView.setupWithNavController(navHostFragment.navController)

        setupInAppReview()
    }

    /**
     * In-App Reviewの設定
     */
    private fun setupInAppReview() {
        val reviewManager = AppInjector.reviewManager(this)
        val request = reviewManager.requestReviewFlow()
        request.addOnCompleteListener { task: Task<ReviewInfo> ->
            if (task.isSuccessful) {
                val reviewInfo = task.result
                val flow = reviewManager.launchReviewFlow(this, reviewInfo)
                flow.addOnCompleteListener {
                    Timber.d("Review Complete")
                }
            } else {
                // 失敗しても無視する
                Timber.e("Review Failed")
            }
        }
    }
}
