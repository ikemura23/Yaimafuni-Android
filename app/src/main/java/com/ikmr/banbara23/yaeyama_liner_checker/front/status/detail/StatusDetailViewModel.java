package com.ikmr.banbara23.yaeyama_liner_checker.front.status.detail;

import android.databinding.ObservableField;
import android.text.TextUtils;

import com.ikmr.banbara23.yaeyama_liner_checker.model.PortStatus;

/**
 * 港詳細のViewModel
 */
public class StatusDetailViewModel {
    public ObservableField<PortStatus> portStatus = new ObservableField<>();

    public boolean hasComment() {
        if (portStatus != null) {
            return false;
        }
        return !TextUtils.isEmpty(portStatus.get().getComment());
    }
}
