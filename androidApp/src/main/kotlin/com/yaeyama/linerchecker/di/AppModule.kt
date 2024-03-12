package com.yaeyama.linerchecker.di

import com.google.android.play.core.review.ReviewManagerFactory
import com.google.android.play.core.review.testing.FakeReviewManager
import org.koin.dsl.module

val appModule = module {
    single {
        if (com.yaeyama.linerchecker.BuildConfig.DEBUG) {
            FakeReviewManager(get())
        } else {
            ReviewManagerFactory.create(get())
        }
    }
}
