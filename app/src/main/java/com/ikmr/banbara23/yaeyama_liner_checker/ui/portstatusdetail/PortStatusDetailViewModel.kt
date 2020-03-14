package com.ikmr.banbara23.yaeyama_liner_checker.ui.portstatusdetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ikmr.banbara23.yaeyama_liner_checker.api.ApiClient
import com.ikmr.banbara23.yaeyama_liner_checker.core.LiveEvent
import com.ikmr.banbara23.yaeyama_liner_checker.model.Company
import com.ikmr.banbara23.yaeyama_liner_checker.model.DetailLinerInfo
import com.ikmr.banbara23.yaeyama_liner_checker.model.PortStatus
import com.ikmr.banbara23.yaeyama_liner_checker.model.StatusDetailRoot

class PortStatusDetailViewModel : ViewModel() {
    val statusDetailRoot = MutableLiveData<StatusDetailRoot>()
    val portStatus = MutableLiveData<PortStatus>()
    val detailLinerInfo = MutableLiveData<DetailLinerInfo>()

    private val apiClient = ApiClient()

    /**
     * 天気取得
     */
    fun load(company: Company, portCode: String) {
        apiClient.getDetailInfo(company, portCode).subscribe {
            statusDetailRoot.postValue(it)
        }
    }

    sealed class Nav : LiveEvent {
        object Error : Nav()
        data class Web(val url: String) : Nav()
        data class Tell(val tellNo: String) : Nav()
    }
}

