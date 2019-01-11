package com.ikmr.banbara23.yaeyama_liner_checker.front.status.detail

import com.ikmr.banbara23.yaeyama_liner_checker.api.ApiClient
import com.ikmr.banbara23.yaeyama_liner_checker.front.base.Presenter
import com.ikmr.banbara23.yaeyama_liner_checker.model.Company
import com.ikmr.banbara23.yaeyama_liner_checker.model.DetailLinerInfo
import com.ikmr.banbara23.yaeyama_liner_checker.model.PortStatus
import com.ikmr.banbara23.yaeyama_liner_checker.model.time_table.TimeTable

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subscribers.ResourceSubscriber

/**
 * 運行詳細 Presenter
 */
class StatusDetailPresenter internal constructor(private val viewModel: StatusDetailViewModel,
    private val linerViewModel: LinerInfoViewModel, private val timeTableViewModel: TimeTableViewModel,
    private val company: Company, private val portCode: String) : Presenter<StatusDetailView> {
    private val TAG = StatusDetailPresenter::class.java.simpleName
    private var view: StatusDetailView? = null
    private val mDisposable = CompositeDisposable()

    override fun attachView(view: StatusDetailView) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    fun onResume() {
        // 運行のステータス + ステータス文字の背景色
        mDisposable.add(
            ApiClient.getStatusDetail(company, portCode)
                    .subscribeWith(object : ResourceSubscriber<PortStatus>() {
                        override fun onNext(portStatus: PortStatus) {
                            setViewModel(portStatus)
                        }

                        override fun onError(t: Throwable) {
                        }

                        override fun onComplete() {
                        }
                    })
        )
        // 運行関連情報（値段や時間）
        mDisposable.add(
            ApiClient.getDetailLinerInfo(company, portCode)
                    .subscribeWith(object : ResourceSubscriber<DetailLinerInfo>() {
                        override fun onNext(info: DetailLinerInfo) {
                            setViewModelOfLinerInfo(info)
                        }

                        override fun onError(t: Throwable) {
                        }

                        override fun onComplete() {
                        }
                    })
        )
        // 時間別の運行ステータス
        mDisposable.add(
            ApiClient.getTimeTable(company, portCode)
                    .subscribeWith(object : ResourceSubscriber<TimeTable>() {
                        override fun onNext(timeTable: TimeTable) {
                            setTimeTableViewModel(timeTable)
                        }

                        override fun onError(t: Throwable) {
                            timeTableViewModel.canShow.set(false)
                        }

                        override fun onComplete() {
                        }
                    })
        )
    }

    fun onStop() {
        mDisposable.clear()
    }

    private fun setTimeTableViewModel(timeTable: TimeTable) {
        if (timeTable.getRow() == null || timeTable.getRow().isEmpty()) {
            timeTableViewModel.canShow.set(false)
            return
        }

        if (timeTable.getHeader() != null) {
            timeTableViewModel.header.set(timeTable.getHeader())
        }
        if (timeTable.getRow() != null || !timeTable.getRow().isEmpty()) {
            timeTableViewModel.rows.clear()
            timeTableViewModel.rows.addAll(timeTable.getRow())
        }
        timeTableViewModel.canShow.set(true)
    }

    /**
     * ViewModelにセット
     *
     * @param portStatus
     */
    private fun setViewModel(portStatus: PortStatus) {
        // 運行ステータス
        viewModel.portStatus.set(portStatus)
        // 運行ステータスの背景色
        viewModel.setStatusColor(portStatus.status.code)
    }

    /**
     * ViewModelにセット
     *
     * @param detailLinerInfo
     */
    private fun setViewModelOfLinerInfo(detailLinerInfo: DetailLinerInfo) {
        // ViewModelにセット
        linerViewModel.detailLinerInfo.set(detailLinerInfo)
    }

    /**
     * 外部電話アプリを起動
     */
    fun startTel() {
        if (linerViewModel.detailLinerInfo.get() == null) {
            return
        }
        view!!.openTell(linerViewModel.detailLinerInfo.get()!!.tell)
    }

    /**
     * ブラウザを起動
     */
    fun startWeb() {
        if (linerViewModel.detailLinerInfo.get() == null) {
            return
        }
        view!!.openBrowser(linerViewModel.detailLinerInfo.get()!!.url)
    }
}
