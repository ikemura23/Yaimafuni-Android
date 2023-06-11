package com.yaeyama.linerchecker.di

import android.content.Context
import com.google.android.play.core.review.ReviewManager
import com.google.android.play.core.review.ReviewManagerFactory
import com.google.android.play.core.review.testing.FakeReviewManager

object AppInjector {
    fun reviewManager(context: Context): ReviewManager {
        return if (com.yaeyama.linerchecker.BuildConfig.DEBUG) {
            FakeReviewManager(context)
        } else {
            ReviewManagerFactory.create(context)
        }
    }
}
