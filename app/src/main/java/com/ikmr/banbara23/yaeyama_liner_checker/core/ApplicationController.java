
package com.ikmr.banbara23.yaeyama_liner_checker.core;

import android.app.Application;

import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.FirebaseDatabase;
import com.ikmr.banbara23.yaeyama_liner_checker.R;

public class ApplicationController extends Application {

    private static ApplicationController mApplicationController;

    @Override
    public void onCreate() {
        super.onCreate();

        if (mApplicationController == null) {
            mApplicationController = this;
        }
        Base.initialize(this);

        MobileAds.initialize(this, getString(R.string.ad_unit_id));
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }

    public static synchronized ApplicationController getInstance() {
        return mApplicationController;
    }
}
