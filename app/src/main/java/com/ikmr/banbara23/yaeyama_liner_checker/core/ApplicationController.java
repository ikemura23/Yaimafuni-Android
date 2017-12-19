
package com.ikmr.banbara23.yaeyama_liner_checker.core;

import android.app.Application;

import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.FirebaseDatabase;
import com.ikmr.banbara23.yaeyama_liner_checker.common.Constants;

public class ApplicationController extends Application {

    private static ApplicationController mApplicationController;

    @Override
    public void onCreate() {
        super.onCreate();

        if (mApplicationController == null) {
            mApplicationController = this;
        }
        Base.initialize(this);

        MobileAds.initialize(this, Constants.AD_UNIT_ID);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }

    public static synchronized ApplicationController getInstance() {
        return mApplicationController;
    }
}
