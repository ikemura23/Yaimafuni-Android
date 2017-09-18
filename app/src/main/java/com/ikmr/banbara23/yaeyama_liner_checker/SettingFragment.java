
package com.ikmr.banbara23.yaeyama_liner_checker;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;

/**
 * PreferenceFragment継承
 */
public class SettingFragment extends PreferenceFragment {
    private static final String TAG = SettingFragment.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);

        PreferenceScreen preferenceScreen = (PreferenceScreen) findPreference("info_preference");
        preferenceScreen.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(
                        getActivity(),
                        com.ikmr.banbara23.yaeyama_liner_checker.OtherActivity.class);
                startActivity(intent);
                return true;
            }
        });
    }
}
