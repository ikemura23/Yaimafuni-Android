package com.yaeyama.linerchecker.di

import com.google.android.play.core.review.testing.FakeReviewManager
import org.koin.dsl.module

val appModule = module {
    single {
        FakeReviewManager(get())
    }
}
