package com.ikmr.banbara23.yaeyama_liner_checker.front.status.detail;

import android.databinding.ObservableField;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.core.Base;
import com.ikmr.banbara23.yaeyama_liner_checker.model.PortStatus;

/**
 * 港詳細のViewModel
 */
public class StatusDetailViewModel {
    public ObservableField<PortStatus> portStatus = new ObservableField<>();

    public ObservableField<Drawable> statusImage = new ObservableField<>();

    public void setStatusDrawable(String code) {
        Log.d("StatusDetailViewModel", "portStatus.get():" + portStatus.get());
        Drawable drawable;

        switch (code) {
            case "normal":
                drawable = ContextCompat.getDrawable(Base.getContext(), R.drawable.ic_status_normal);
                break;
            case "cancel":
                drawable = ContextCompat.getDrawable(Base.getContext(), R.drawable.ic_status_cancel);
                break;
            case "suspend":
                drawable = ContextCompat.getDrawable(Base.getContext(), R.drawable.ic_status_cancel);
                break;
            case "cation":
            default:
                drawable = ContextCompat.getDrawable(Base.getContext(), R.drawable.ic_status_cation);
        }
        statusImage.set(drawable);
    }
}
