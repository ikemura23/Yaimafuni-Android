package com.yaeyama.linerchecker.di

import com.google.android.play.core.review.ReviewManagerFactory
import org.koin.dsl.module

val appModule = module {
    single {
        ReviewManagerFactory.create(get())
    }
}
