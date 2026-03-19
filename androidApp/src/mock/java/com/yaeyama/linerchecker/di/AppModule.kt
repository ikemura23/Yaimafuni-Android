package com.yaeyama.linerchecker.di

import com.google.android.play.core.review.ReviewManager
import com.google.android.play.core.review.testing.FakeReviewManager
import org.koin.dsl.module

val appModule = module {
    single<ReviewManager> {
        FakeReviewManager(get())
    }
}
