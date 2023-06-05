package com.ikmr.banbara23.yaeyama_liner_checker.ui.portstatusdetail

import androidx.lifecycle.ViewModel
import data.repository.StatusDetailRepository
import data.usecase.GetStatusDetail

/**
 * 運行詳細 ViewModel
 */
class PortStatusDetailViewModel : ViewModel() {
    // UseCaseでFlowを受け取る
    val getStatusDetail = GetStatusDetail(StatusDetailRepository())
}
