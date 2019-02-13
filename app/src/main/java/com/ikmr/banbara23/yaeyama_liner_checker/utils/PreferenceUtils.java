package com.ikmr.banbara23.yaeyama_liner_checker.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.ikmr.banbara23.yaeyama_liner_checker.core.ApplicationController;
import com.ikmr.banbara23.yaeyama_liner_checker.core.Base;

public final class PreferenceUtils {

    private static SharedPreferences getDefaultSharedPreferences(final Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    private static Context getContext() {
        return ApplicationController.getInstance().getApplicationContext();
    }

    public static void saveInt(String key, int value) {
        Context context = Base.getContext();
        SharedPreferences.Editor editor = getDefaultSharedPreferences(context).edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static void saveLong(String key, long value) {
        SharedPreferences.Editor editor = getDefaultSharedPreferences(getContext()).edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public static void saveString(String key, String value) {
        SharedPreferences.Editor editor = getDefaultSharedPreferences(getContext()).edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static int loadInt(String key) {
        return getDefaultSharedPreferences(Base.getContext()).getInt(key, 0);
    }

    protected static String loadString(String key) {
        return getDefaultSharedPreferences(getContext()).getString(key, null);
    }

    protected static long loadLong(String key) {
        return getDefaultSharedPreferences(getContext()).getLong(key, 0);
    }

    public static void remove(Context context, String key) {
        SharedPreferences.Editor editor = getDefaultSharedPreferences(context).edit();
        editor.remove(key);
        editor.apply();
    }

    public static boolean getBoolean(final String key) {
        return getDefaultSharedPreferences(getContext()).getBoolean(key, true);
    }

    private PreferenceUtils() {
    }
}
