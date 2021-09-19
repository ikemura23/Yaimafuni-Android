package com.ikmr.banbara23.yaeyama_liner_checker

import android.app.Application
import com.google.firebase.crashlytics.BuildConfig
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.ikemura.shared.di.initKoin
import timber.log.Timber

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Crashlyticsはdebugでは無効にする
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(!BuildConfig.DEBUG)

        // Koin
        initKoin()

        // デバッグビルドのみログ出力
        Timber.plant(Timber.DebugTree())
    }
}
