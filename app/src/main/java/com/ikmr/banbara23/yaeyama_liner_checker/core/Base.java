package com.ikmr.banbara23.yaeyama_liner_checker.core;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import androidx.annotation.NonNull;

/**
 * Base helps to get {@link Context}, {@link Resources}, {@link AssetManager},
 * {@link Configuration} and {@link DisplayMetrics} in any class.
 */
public class Base {

    private static Context context;

    public static void initialize(@NonNull Context context) {
        Base.context = context;
    }

    public static Context getContext() {
        synchronized (Base.class) {
            if (Base.context == null) {
                throw new NullPointerException("Call Base.initialize(context) within your Application onCreate() method.");
            }

            return Base.context.getApplicationContext();
        }
    }

    public static Resources getResources() {
        return Base.getContext().getResources();
    }

    public static Resources.Theme getTheme() {
        return Base.getContext().getTheme();
    }

    public static AssetManager getAssets() {
        return Base.getContext().getAssets();
    }

    public static Configuration getConfiguration() {
        return Base.getResources().getConfiguration();
    }

    public static DisplayMetrics getDisplayMetrics() {
        return Base.getResources().getDisplayMetrics();
    }
}
