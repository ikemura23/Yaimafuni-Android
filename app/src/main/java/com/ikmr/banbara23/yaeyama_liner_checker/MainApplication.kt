package com.ikmr.banbara23.yaeyama_liner_checker

import android.app.Application
import com.google.firebase.database.FirebaseDatabase
import com.ikemura.shared.initKoin
import timber.log.Timber

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        FirebaseDatabase.getInstance().setPersistenceEnabled(false)

        // Koin
        initKoin()

        // デバッグビルドのみログ出力
        Timber.plant(Timber.DebugTree())
    }
}
