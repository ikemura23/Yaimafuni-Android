package com.yaeyama.linerchecker.ui.main

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.content.edit
import com.google.android.gms.tasks.Task
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManager
import com.yaeyama.linerchecker.ui.dashboard.DashBoardViewModel
import com.yaeyama.linerchecker.ui.main.compose.MainScreen
import com.yaeyama.linerchecker.ui.theme.YaimafuniAndroidTheme
import com.yaeyama.linerchecker.ui.typhoon.list.TyphoonListViewModel
import com.yaeyama.linerchecker.ui.weather.WeatherViewModel
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
        // アプリはダークモード非対応のため、システムのダークモード設定に関わらず常にlightスタイル(濃色アイコン)にする
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT),
        )

        if (isShowReview()) showInAppReview()
        countUpLaunchCount()

        setContent {
            YaimafuniAndroidTheme {
                MainScreen(
                    mainViewModel = mainViewModel,
                    weatherViewModel = weatherViewModel,
                    dashboardViewModel = dashboardViewModel,
                    typhoonListViewModel = typhoonListViewModel,
                )
            }
        }
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
