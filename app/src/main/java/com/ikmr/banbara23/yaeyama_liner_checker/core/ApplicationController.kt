package com.ikmr.banbara23.yaeyama_liner_checker.core

import android.app.Application
import com.google.firebase.database.FirebaseDatabase
import timber.log.Timber

class ApplicationController : Application() {

    override fun onCreate() {
        super.onCreate()

        Base.initialize(this)

        FirebaseDatabase.getInstance().setPersistenceEnabled(false)

        // デバッグビルドのみログ出力
        Timber.plant(Timber.DebugTree())
    }
}
