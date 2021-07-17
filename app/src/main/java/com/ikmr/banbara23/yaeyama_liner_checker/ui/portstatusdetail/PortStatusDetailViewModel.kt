package com.ikmr.banbara23.yaeyama_liner_checker.ui.portstatusdetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ikemura.shared.model.statusdetail.Company
import com.ikmr.banbara23.yaeyama_liner_checker.api.ApiClient
import com.ikmr.banbara23.yaeyama_liner_checker.model.StatusDetailRoot
import io.reactivex.disposables.Disposable
import timber.log.Timber

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
                Timber.e(it)
            }, {
                Timber.d("complete")
            })
    }

    fun dispose() {
        disposable.dispose()
    }
}
