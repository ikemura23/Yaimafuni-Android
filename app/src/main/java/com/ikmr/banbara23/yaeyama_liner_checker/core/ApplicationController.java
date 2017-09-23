
package com.ikmr.banbara23.yaeyama_liner_checker.core;

import android.app.Application;

public class ApplicationController extends Application {

    private static ApplicationController mApplicationController;

    @Override
    public void onCreate() {
        super.onCreate();

        if (mApplicationController == null) {
            mApplicationController = this;
        }
        Base.initialize(this);
//        NCMB.initialize(Base.getContext(),
//                BuildConfig.NCMB_APPLICATION_ID,
//                BuildConfig.NCMB_CLIENT_KEY);

//        AnalyticsUtils.initialize(getApplicationContext());
    }

    public static synchronized ApplicationController getInstance() {
        return mApplicationController;
    }
}
