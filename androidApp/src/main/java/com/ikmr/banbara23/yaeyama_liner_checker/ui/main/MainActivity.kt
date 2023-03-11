package com.ikmr.banbara23.yaeyama_liner_checker.ui.main

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.tasks.Task
import com.ikmr.banbara23.yaeyama_liner_checker.R
import com.ikmr.banbara23.yaeyama_liner_checker.databinding.MainActivityBinding
import com.ikmr.banbara23.yaeyama_liner_checker.di.AppInjector
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * ホーム画面、Bottom NavigationのあるActivity
 */
class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel = MainViewModel()
    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MainActivityBinding.inflate(layoutInflater)

        val navView: BottomNavigationView = binding.navView

        // val navController = findNavController(R.id.nav_host_fragment) <= ビルドは成功するが起動するとエラーとなる、↓で解決
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navView.setupWithNavController(navHostFragment.navController)

        if (isShowReview()) showInAppReview()
        countUpLaunchCount()

        setupBottomNavBadge()

        setContentView(binding.root)
    }

    /**
     * ボトムナビゲーションの台風バッジの設定
     */
    private fun setupBottomNavBadge() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                mainViewModel.existsTyphoon().collect { result ->
                    handleTyphoonBadge(result)
                }
            }
        }
    }

    /**
     * バッジ表示のハンドリング
     */
    private fun handleTyphoonBadge(typhoonCount: Int) {
        binding.navView.getOrCreateBadge(R.id.typhoonListFragment).also { badgeDrawable ->
            badgeDrawable.backgroundColor = ContextCompat.getColor(this, R.color.badge_color)
            // バッジ数字を設定
            if (typhoonCount > 0) {
                badgeDrawable.number = typhoonCount
            } else {
                badgeDrawable.clearNumber()
            }
        }.isVisible = typhoonCount > 0
    }

    /**
     * In-App Reviewの設定
     */
    private fun showInAppReview() {
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

    private fun isShowReview(): Boolean {
        val count = getLaunchCount()
        Timber.d("launch count: $count")
        return count > 0 && count % 5 == 0 // 5回ずつの起動が条件
    }

    private fun getLaunchCount(): Int {
        val sharedPreferences = getPreferences(Context.MODE_PRIVATE)
        return sharedPreferences.getInt(KEY_REVIEW, 0)
    }

    private fun countUpLaunchCount() {
        val count = getLaunchCount()
        getPreferences(Context.MODE_PRIVATE).edit {
            putInt(KEY_REVIEW, count + 1)
        }
    }

    companion object {
        private const val KEY_REVIEW = "KEY_REVIEW"
    }
}
