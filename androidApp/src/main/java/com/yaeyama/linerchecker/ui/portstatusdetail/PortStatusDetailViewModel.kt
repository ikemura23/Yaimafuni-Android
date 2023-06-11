package com.yaeyama.linerchecker.ui.portstatusdetail

import androidx.lifecycle.ViewModel
import com.yaeyama.linerchecker.repository.StatusDetailRepository
import com.yaeyama.linerchecker.usecase.GetStatusDetail

/**
 * 運行詳細 ViewModel
 */
class PortStatusDetailViewModel : ViewModel() {
    // UseCaseでFlowを受け取る
    val getStatusDetail = GetStatusDetail(StatusDetailRepository())
}
