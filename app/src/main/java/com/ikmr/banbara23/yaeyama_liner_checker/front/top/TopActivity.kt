package com.ikmr.banbara23.yaeyama_liner_checker.front.top

import android.os.Bundle
import com.ikmr.banbara23.yaeyama_liner_checker.R
import com.ikmr.banbara23.yaeyama_liner_checker.core.BaseActivity
import com.ikmr.banbara23.yaeyama_liner_checker.di.AppInjector.reviewManager
import timber.log.Timber

class TopActivity : BaseActivity() {
    private val reviewManager = reviewManager(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.top_activity)

        setupInAppReview()
    }

    private fun setupInAppReview() {
        val request = reviewManager.requestReviewFlow()
        request.addOnCompleteListener { request ->
            if (request.isSuccessful) {
                val reviewInfo = request.result
                val flow = reviewManager.launchReviewFlow(this, reviewInfo)
                flow.addOnCompleteListener {
                    Timber.d("complete")
                }
            } else {
                // 失敗しても無視する
                Timber.d("failed")
            }
        }
    }
}
