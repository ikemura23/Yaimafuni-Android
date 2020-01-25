package com.ikmr.banbara23.yaeyama_liner_checker.front.setting

import android.content.Intent
import android.os.Bundle
import android.preference.Preference
import android.preference.PreferenceFragment
import android.preference.PreferenceScreen

import com.ikmr.banbara23.yaeyama_liner_checker.R
import com.ikmr.banbara23.yaeyama_liner_checker.front.info.InfoActivity

/**
 * PreferenceFragment継承
 */
class SettingFragment : PreferenceFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addPreferencesFromResource(R.xml.preferences)

        val preferenceScreen = findPreference("info_preference") as PreferenceScreen
        preferenceScreen.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            val intent = Intent(
                activity,
                InfoActivity::class.java
            )
            startActivity(intent)
            true
        }
    }

    companion object {
        private val TAG = SettingFragment::class.java.simpleName
    }
}
