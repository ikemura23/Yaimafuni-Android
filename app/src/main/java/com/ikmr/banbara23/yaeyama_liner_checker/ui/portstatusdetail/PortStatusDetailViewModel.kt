package com.ikmr.banbara23.yaeyama_liner_checker.ui.portstatusdetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DatabaseReference
import com.ikmr.banbara23.yaeyama_liner_checker.api.ApiClient
import com.ikmr.banbara23.yaeyama_liner_checker.core.LiveEvent
import com.ikmr.banbara23.yaeyama_liner_checker.core.SingleLiveEvent
import com.ikmr.banbara23.yaeyama_liner_checker.model.Company
import com.ikmr.banbara23.yaeyama_liner_checker.model.DetailLinerInfo
import com.ikmr.banbara23.yaeyama_liner_checker.model.PortStatus
import com.ikmr.banbara23.yaeyama_liner_checker.model.StatusDetailRoot
import com.ikmr.banbara23.yaeyama_liner_checker.model.time_table.TimeTable
import io.reactivex.disposables.CompositeDisposable

class PortStatusDetailViewModel : ViewModel() {
    val statusDetailRoot = MutableLiveData<StatusDetailRoot>()
    val portStatus = MutableLiveData<PortStatus>()
    val detailLinerInfo = MutableLiveData<DetailLinerInfo>()
    val timeTable = MutableLiveData<TimeTable>()

    val disposable = CompositeDisposable()

    private lateinit var database: DatabaseReference
    var event = SingleLiveEvent<Nav>()

    private val apiClient = ApiClient()

    /**
     * 天気取得
     */
    fun load(company: Company, portCode: String) {
        disposable.add(
            apiClient.getDetailInfo(company, portCode).subscribe {
                statusDetailRoot.postValue(it)
            })
        // apiClient.getDetailInfo(company, portCode)
        //     .subscribeOn(Schedulers.io())
        //     .observeOn(AndroidSchedulers.mainThread())
        //     .subscribe(object : FlowableSubscriber<StatusDetailRoot> {
        //         override fun onComplete() {
        //             // 処理なし
        //         }
        //
        //         override fun onSubscribe(s: Subscription) {
        //             // 処理なし
        //         }
        //
        //         override fun onNext(root: StatusDetailRoot) {
        //             logD(root)
        //             statusDetailRoot.postValue(root)
        //         }
        //
        //         override fun onError(t: Throwable) {
        //             logE(t)
        //         }
        //     })

        // database = FirebaseDatabase.getInstance().reference.ref
        // // 運行のステータス + ステータス文字の背景色
        // database.child("${company.code}/$portCode").addValueEventListener(object : ValueEventListener {
        //     override fun onCancelled(error: DatabaseError) {
        //         logE(error)
        //         event.postValue(Nav.Error)
        //     }
        //
        //     override fun onDataChange(snapshot: DataSnapshot) {
        //         portStatus.value = snapshot.getValue(PortStatus::class.java)
        //         portStatus.value?.let { logD(it) }
        //     }
        // })
        // // 運行関連情報（値段や時間）
        // database.child("${company.code}_liner_info/$portCode").addValueEventListener(object : ValueEventListener {
        //     override fun onCancelled(error: DatabaseError) {
        //         logE(error)
        //         event.postValue(Nav.Error)
        //     }
        //
        //     override fun onDataChange(snapshot: DataSnapshot) {
        //         detailLinerInfo.value = snapshot.getValue(DetailLinerInfo::class.java)
        //         detailLinerInfo.value?.let { logD(it) }
        //     }
        // })
        // // 時間別の運行ステータス
        // database.child("${company.code}_timeTable/$portCode").addValueEventListener(object : ValueEventListener {
        //     override fun onCancelled(error: DatabaseError) {
        //         logE(error)
        //         event.postValue(Nav.Error)
        //     }
        //
        //     override fun onDataChange(snapshot: DataSnapshot) {
        //         timeTable.value = snapshot.getValue(TimeTable::class.java)
        //         timeTable.value?.let { logD(it) }
        //     }
        // })
    }

    /**
     * 外部電話アプリを起動
     */
    fun startTel() {
        detailLinerInfo.value?.let { info ->
            event.setValue(Nav.Tell(info.tell))
        }
    }

    /**
     * ブラウザを起動
     */
    fun startWeb() {
        detailLinerInfo.value?.let { info ->
            event.setValue(Nav.Web(info.url))
        }
    }

    sealed class Nav : LiveEvent {
        object Error : Nav()
        data class Web(val url: String) : Nav()
        data class Tell(val tellNo: String) : Nav()
    }
}

