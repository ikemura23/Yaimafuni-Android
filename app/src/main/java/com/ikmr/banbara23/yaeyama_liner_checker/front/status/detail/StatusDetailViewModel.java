package com.ikmr.banbara23.yaeyama_liner_checker.front.status.detail;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;

import com.ikmr.banbara23.yaeyama_liner_checker.common.StatusHelper;
import com.ikmr.banbara23.yaeyama_liner_checker.model.PortStatus;

/**
 * 港詳細のViewModel
 */
public class StatusDetailViewModel {
    public ObservableField<PortStatus> portStatus = new ObservableField<>();

    public ObservableInt statusColor = new ObservableInt();

    public void setStatusColor(String code) {
        statusColor.set(StatusHelper.getStatusColor(code));
    }
}
