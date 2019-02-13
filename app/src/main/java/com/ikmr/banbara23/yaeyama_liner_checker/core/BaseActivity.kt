package com.ikmr.banbara23.yaeyama_liner_checker.core

import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate

open class BaseActivity : AppCompatActivity() {
    companion object {
        init {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        }
    }
}
