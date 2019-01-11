package com.ikmr.banbara23.yaeyama_liner_checker.front.status.detail

import android.databinding.ObservableField
import android.databinding.ObservableInt

import com.ikmr.banbara23.yaeyama_liner_checker.common.StatusHelper
import com.ikmr.banbara23.yaeyama_liner_checker.model.PortStatus

/**
 * 港詳細のViewModel
 */
class StatusDetailViewModel {
    var portStatus = ObservableField<PortStatus>()

    var statusColor = ObservableInt()

    fun setStatusColor(code: String) {
        statusColor.set(StatusHelper.getStatusColor(code))
    }
}
