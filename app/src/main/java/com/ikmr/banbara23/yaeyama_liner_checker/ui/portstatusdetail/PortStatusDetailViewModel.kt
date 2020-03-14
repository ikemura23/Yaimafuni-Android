package com.ikmr.banbara23.yaeyama_liner_checker.ui.portstatusdetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ikmr.banbara23.yaeyama_liner_checker.api.ApiClient
import com.ikmr.banbara23.yaeyama_liner_checker.ext.logD
import com.ikmr.banbara23.yaeyama_liner_checker.ext.logE
import com.ikmr.banbara23.yaeyama_liner_checker.model.Company
import com.ikmr.banbara23.yaeyama_liner_checker.model.StatusDetailRoot
import io.reactivex.disposables.Disposable

/**
 * 運行詳細 ViewModel
 */
class PortStatusDetailViewModel : ViewModel() {
    val statusDetailRoot = MutableLiveData<StatusDetailRoot>()
    private val apiClient = ApiClient()
    private lateinit var disposable: Disposable

    /**
     * 天気取得
     */
    fun load(company: Company, portCode: String) {
        disposable = apiClient.getDetailInfo(company, portCode).subscribe(
            { data ->
                statusDetailRoot.postValue(data)
            }, {
                logE(it)
            }, {
                logD("complete")
            })
    }

    fun dispose() {
        disposable.dispose()
    }
}
