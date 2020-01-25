package com.ikmr.banbara23.yaeyama_liner_checker.core

import android.content.Context
import android.support.multidex.MultiDex
import android.support.multidex.MultiDexApplication
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.core.CrashlyticsCore
import com.google.firebase.database.FirebaseDatabase
import com.ikmr.banbara23.yaeyama_liner_checker.BuildConfig
import io.fabric.sdk.android.Fabric

class ApplicationController : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        if (instance == null) {
            instance = this
        }
        Base.initialize(this)

        FirebaseDatabase.getInstance().setPersistenceEnabled(false)
        val crashlytics = Crashlytics.Builder()
            .core(
                CrashlyticsCore.Builder()
                    .disabled(BuildConfig.DEBUG).build()
            )
            .build()
        Fabric.with(this, crashlytics)
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
