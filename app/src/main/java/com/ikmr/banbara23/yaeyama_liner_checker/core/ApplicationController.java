
package com.ikmr.banbara23.yaeyama_liner_checker.core;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class ApplicationController extends Application {

    private static ApplicationController mApplicationController;

    @Override
    public void onCreate() {
        super.onCreate();

        if (mApplicationController == null) {
            mApplicationController = this;
        }
        Base.initialize(this);

//        AnalyticsUtils.initialize(getApplicationContext());
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }

    public static synchronized ApplicationController getInstance() {
        return mApplicationController;
    }
}
