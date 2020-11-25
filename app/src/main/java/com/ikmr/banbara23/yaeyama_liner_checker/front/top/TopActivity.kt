package com.ikmr.banbara23.yaeyama_liner_checker.front.top

import android.os.Bundle
import com.google.android.play.core.review.ReviewManagerFactory

import com.ikmr.banbara23.yaeyama_liner_checker.R
import com.ikmr.banbara23.yaeyama_liner_checker.core.BaseActivity

class TopActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.top_activity)

        setupInAppReview()
    }

    private fun setupInAppReview() {
        val manager = ReviewManagerFactory.create(this)
        val request = manager.requestReviewFlow()
        request.addOnCompleteListener { request ->
            if (request.isSuccessful) {
                // We got the ReviewInfo object
                val reviewInfo = request.result
            } else {
                // There was some problem, continue regardless of the result.
            }
        }
    }
}
