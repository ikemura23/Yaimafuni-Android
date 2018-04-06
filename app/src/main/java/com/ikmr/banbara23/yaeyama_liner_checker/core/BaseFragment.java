
package com.ikmr.banbara23.yaeyama_liner_checker.core;

import android.content.Context;

public class BaseFragment extends android.support.v4.app.Fragment {
    public Context getContext() {
        return getActivity().getApplicationContext();
    }
}
