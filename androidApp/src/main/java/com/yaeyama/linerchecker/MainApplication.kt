package com.yaeyama.linerchecker

import android.app.Application
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.yaeyama.linerchecker.BuildConfig
import com.yaeyama.linerchecker.di.appModule
import com.yaeyama.linerchecker.di.dataModule
import com.yaeyama.linerchecker.di.useCaseModule
import com.yaeyama.linerchecker.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import timber.log.Timber

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Crashlyticsはdebugでは無効にする
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(!BuildConfig.DEBUG)

        // Koin
        initKoin()

        // デバッグビルドのみログ出力
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun initKoin(): KoinApplication = startKoin {
        androidContext(this@MainApplication)
        modules(
            appModule,
            dataModule,
            useCaseModule,
            viewModelModule,
        )
    }
}
