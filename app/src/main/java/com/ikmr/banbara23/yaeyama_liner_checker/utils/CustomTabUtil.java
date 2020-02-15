package com.ikmr.banbara23.yaeyama_liner_checker.utils;

import android.app.Activity;
import android.net.Uri;
import android.text.TextUtils;

import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;

import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.common.Constants;

public class CustomTabUtil {

    public static void start(Activity activity, String urlString) {
        if (TextUtils.isEmpty(urlString)) {
            urlString = Constants.weatherUrl;
        }

        Uri uri = Uri.parse(urlString);

        final CustomTabsIntent tabsIntent = new CustomTabsIntent.Builder()
                .setShowTitle(true)
                .setToolbarColor(ContextCompat.getColor(activity, R.color.primary))
                .build();

        // Chromeの起動
        tabsIntent.launchUrl(activity, uri);
    }
}
