package com.ikmr.banbara23.yaeyama_liner_checker.ui.companylist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ikmr.banbara23.yaeyama_liner_checker.model.CompanyStatus
import io.reactivex.disposables.CompositeDisposable

class CompanyListViewModel : ViewModel() {
    private val _companyStatus = MutableLiveData<CompanyStatus>()
    val companyStatus: LiveData<CompanyStatus> = _companyStatus
    private val disposable = CompositeDisposable()

    // fun load(company: Company) {
    //     disposable.add(
    //         ApiClient()
    //             .getCompanyStatus(company.code)
    //             .subscribeWith(object : ResourceSubscriber<CompanyStatus>() {
    //                 override fun onComplete() {
    //                 }
    //
    //                 override fun onNext(companyStatus: CompanyStatus) {
    //                     _companyStatus.value = companyStatus
    //                 }
    //
    //                 override fun onError(t: Throwable) {
    //                     Timber.e(t)
    //                 }
    //             })
    //     )
    // }
}
