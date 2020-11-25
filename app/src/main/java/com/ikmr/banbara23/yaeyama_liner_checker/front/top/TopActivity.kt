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
                Timber.d("successful")
                // We got the ReviewInfo object
                val reviewInfo = request.result
                val flow = reviewManager.launchReviewFlow(this, reviewInfo)
                flow.addOnCompleteListener { _ ->
                    Timber.d("onCompleteListener")
                    // The flow has finished. The API does not indicate whether the user
                    // reviewed or not, or even whether the review dialog was shown. Thus, no
                    // matter the result, we continue our app flow.
                }
            } else {
                Timber.d("not successful")
                // There was some problem, continue regardless of the result.
            }
        }
    }
}
