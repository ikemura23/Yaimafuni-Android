package com.ikmr.banbara23.yaeyama_liner_checker.ui.portstatusdetail

import androidx.lifecycle.ViewModel
import com.ikemura.shared.repository.StatusDetailRepository
import com.ikemura.shared.usecase.GetStatusDetail

/**
 * 運行詳細 ViewModel
 */
class PortStatusDetailViewModel : ViewModel() {
    // UseCaseでFlowを受け取る
    val getStatusDetail = GetStatusDetail(StatusDetailRepository())
}
