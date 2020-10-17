package com.ikmr.banbara23.yaeyama_liner_checker.core

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.google.firebase.database.FirebaseDatabase
import timber.log.Timber

class ApplicationController : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        if (instance == null) {
            instance = this
        }
        Base.initialize(this)

        FirebaseDatabase.getInstance().setPersistenceEnabled(false)

        // デバッグビルドのみログ出力
        Timber.plant(Timber.DebugTree())
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    companion object {

        @get:Synchronized
        var instance: ApplicationController? = null
            private set
    }
}
