package com.ikmr.banbara23.yaeyama_liner_checker.common;

import android.support.v4.content.ContextCompat;

import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.core.Base;

public class StatusHelper {

    public static int getStatusColor(String code) {
        if (code == null) {
            return ContextCompat.getColor(Base.getContext(), R.color.status_cation);
        }
        switch (code) {
            case "normal":
                return ContextCompat.getColor(Base.getContext(), R.color.status_normal);
            case "cancel":
            case "suspend":
                return ContextCompat.getColor(Base.getContext(), R.color.status_cancel);
            case "cation":
            default:
                return ContextCompat.getColor(Base.getContext(), R.color.status_cation);
        }
    }
}
