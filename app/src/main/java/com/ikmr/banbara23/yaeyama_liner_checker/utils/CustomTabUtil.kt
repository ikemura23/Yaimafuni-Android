package com.ikmr.banbara23.yaeyama_liner_checker.utils

import android.app.Activity
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import com.ikmr.banbara23.yaeyama_liner_checker.R

/**
 * カスタムタブ
 */
object CustomTabUtil {
    fun start(activity: Activity, urlString: String) {
        val uri = Uri.parse(urlString)
        val tabsIntent = CustomTabsIntent.Builder()
            .setShowTitle(true)
            .setToolbarColor(ContextCompat.getColor(activity, R.color.primary))
            .build()

        // Chromeの起動
        tabsIntent.launchUrl(activity, uri)
    }
}
