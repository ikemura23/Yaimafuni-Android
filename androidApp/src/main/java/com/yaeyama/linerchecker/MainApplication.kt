package com.yaeyama.linerchecker

import android.app.Application
import com.google.firebase.crashlytics.BuildConfig
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.yaeyama.linerchecker.di.appModule
import com.yaeyama.linerchecker.di.dataModule
import com.yaeyama.linerchecker.di.viewModelModule
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.dsl.module
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

    private fun initKoin(): KoinApplication = startKoin {
        modules(
            module { single { applicationContext } },
            appModule,
            dataModule,
            viewModelModule,
        )
    }
}
