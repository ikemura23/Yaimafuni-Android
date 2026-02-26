package com.yaeyama.linerchecker.ui.main

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.gms.tasks.Task
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManager
import com.yaeyama.linerchecker.ui.dashboard.DashBoardViewModel
import com.yaeyama.linerchecker.ui.main.compose.MainScreen
import com.yaeyama.linerchecker.ui.theme.YaimafuniAndroidTheme
import com.yaeyama.linerchecker.ui.typhoon.list.TyphoonListViewModel
import com.yaeyama.linerchecker.ui.weather.WeatherViewModel
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

/**
 * ホーム画面、Bottom NavigationのあるActivity
 */
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModel()
    private val weatherViewModel: WeatherViewModel by viewModel()
    private val dashboardViewModel: DashBoardViewModel by viewModel()
    private val typhoonListViewModel: TyphoonListViewModel by viewModel()
    private val reviewManager: ReviewManager by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 既存機能を完全保持
        if (isShowReview()) showInAppReview()
        countUpLaunchCount()
        setupBottomNavBadge()

        // Compose統合
        setContent {
            YaimafuniAndroidTheme {
                MainScreen(
                    mainViewModel = mainViewModel,
                    weatherViewModel = weatherViewModel,
                    dashboardViewModel = dashboardViewModel,
                    typhoonListViewModel = typhoonListViewModel,
                    onTyphoonBadgeCountChanged = ::handleTyphoonBadge
                )
            }
        }
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
     * バッジ表示のハンドリング (Compose経由でコールバック)
     */
    private fun handleTyphoonBadge(typhoonCount: Int) {
        // TODO: Composeバッジシステムに統合済みのため、既存のBottomNavigationView処理は不要
        // 将来的にComposeバッジ表示が必要な場合はここで処理
        Timber.d("Typhoon badge count: $typhoonCount")
    }

    /**
     * In-App Reviewの設定
     */
    private fun showInAppReview() {
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
