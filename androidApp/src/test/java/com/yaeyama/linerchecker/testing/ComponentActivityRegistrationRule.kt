package com.yaeyama.linerchecker.testing

import android.content.ComponentName
import android.content.Intent
import android.content.IntentFilter
import androidx.activity.ComponentActivity
import androidx.test.core.app.ApplicationProvider
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.robolectric.Shadows.shadowOf

/**
 * createComposeRule() が起動する ComponentActivity を Robolectric の PackageManager に登録するルール
 *
 * ui-test-manifest は testImplementation ではアプリの AndroidManifest にマージされないため、
 * release を含む全ビルドタイプの JVM テストで動くようにテスト側で登録する。
 * createComposeRule() より先に適用されるよう、order を小さくして使うこと。
 */
class ComponentActivityRegistrationRule : TestWatcher() {
    override fun starting(description: Description) {
        val context = ApplicationProvider.getApplicationContext<android.content.Context>()
        val componentName = ComponentName(context.packageName, ComponentActivity::class.java.name)
        val shadowPackageManager = shadowOf(context.packageManager)
        shadowPackageManager.addActivityIfNotPresent(componentName)
        shadowPackageManager.addIntentFilterForActivity(
            componentName,
            IntentFilter(Intent.ACTION_MAIN).apply { addCategory(Intent.CATEGORY_LAUNCHER) },
        )
    }
}
