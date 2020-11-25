package com.ikmr.banbara23.yaeyama_liner_checker.di

import android.content.Context
import com.google.android.play.core.review.ReviewManager
import com.google.android.play.core.review.ReviewManagerFactory
import com.google.android.play.core.review.testing.FakeReviewManager
import com.ikmr.banbara23.yaeyama_liner_checker.BuildConfig

object AppInjector {
    fun reviewManager(context: Context): ReviewManager {
        return if (BuildConfig.DEBUG) {
            FakeReviewManager(context)
        } else {
            ReviewManagerFactory.create(context)
        }
    }
}
