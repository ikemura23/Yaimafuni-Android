package com.ikmr.banbara23.yaeyama_liner_checker.front.status.detail;

import com.ikmr.banbara23.yaeyama_liner_checker.api.ApiClient;
import com.ikmr.banbara23.yaeyama_liner_checker.front.base.Presenter;
import com.ikmr.banbara23.yaeyama_liner_checker.model.Company;
import com.ikmr.banbara23.yaeyama_liner_checker.model.DetailLinerInfo;
import com.ikmr.banbara23.yaeyama_liner_checker.model.PortStatus;
import com.ikmr.banbara23.yaeyama_liner_checker.model.time_table.TimeTable;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subscribers.ResourceSubscriber;

/**
 * 運行詳細 Presenter
 */
public class StatusDetailPresenter implements Presenter<StatusDetailView> {
    private String TAG = StatusDetailPresenter.class.getSimpleName();
    private StatusDetailViewModel viewModel;
    private LinerInfoViewModel linerViewModel;
    private TimeTableViewModel timeTableViewModel;
    private StatusDetailView view;
    private Company company;
    private String portCode;
    private CompositeDisposable mDisposable = new CompositeDisposable();

    StatusDetailPresenter(StatusDetailViewModel viewModel, LinerInfoViewModel linerViewModel, TimeTableViewModel timeTableViewModel, Company company, String portCode) {
        this.viewModel = viewModel;
        this.linerViewModel = linerViewModel;
        this.timeTableViewModel = timeTableViewModel;
        this.company = company;
        this.portCode = portCode;
    }

    @Override
    public void attachView(StatusDetailView view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
    }

    public void onResume() {
        // 運行のステータス + ステータス文字の背景色
        mDisposable.add(
                ApiClient.getStatusDetail(company, portCode)
                        .subscribeWith(new ResourceSubscriber<PortStatus>() {
                            @Override
                            public void onNext(PortStatus portStatus) {
                                setViewModel(portStatus);
                            }

                            @Override
                            public void onError(Throwable t) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        })
        );
        // 運行関連情報（値段や時間）
        mDisposable.add(
                ApiClient.getDetailLinerInfo(company, portCode)
                        .subscribeWith(new ResourceSubscriber<DetailLinerInfo>() {
                            @Override
                            public void onNext(DetailLinerInfo info) {
                                setViewModelOfLinerInfo(info);
                            }

                            @Override
                            public void onError(Throwable t) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        })
        );
        // 時間別の運行ステータス
        mDisposable.add(
                ApiClient.getTimeTable(company, portCode)
                        .subscribeWith(new ResourceSubscriber<TimeTable>() {
                            @Override
                            public void onNext(TimeTable timeTable) {
                                setTimeTableViewModel(timeTable);
                            }

                            @Override
                            public void onError(Throwable t) {
                                timeTableViewModel.canShow.set(false);
                            }

                            @Override
                            public void onComplete() {

                            }
                        })
        );
    }

    public void onStop() {
        mDisposable.clear();
    }

    private void setTimeTableViewModel(TimeTable timeTable) {
        if (timeTable.getRow() == null || timeTable.getRow().isEmpty()) {
            timeTableViewModel.canShow.set(false);
            return;
        }

        if (timeTable.getHeader() != null) {
            timeTableViewModel.header.set(timeTable.getHeader());
        }
        if (timeTable.getRow() != null || !timeTable.getRow().isEmpty()) {
            timeTableViewModel.rows.clear();
            timeTableViewModel.rows.addAll(timeTable.getRow());
        }
        timeTableViewModel.canShow.set(true);
    }

    /**
     * ViewModelにセット
     *
     * @param portStatus
     */
    private void setViewModel(PortStatus portStatus) {
        // 運行ステータス
        viewModel.portStatus.set(portStatus);
        // 運行ステータスの背景色
        viewModel.setStatusColor(portStatus.getStatus().getCode());
    }

    /**
     * ViewModelにセット
     *
     * @param detailLinerInfo
     */
    private void setViewModelOfLinerInfo(DetailLinerInfo detailLinerInfo) {
        // ViewModelにセット
        linerViewModel.detailLinerInfo.set(detailLinerInfo);
    }

    /**
     * 外部電話アプリを起動
     */
    public void startTel() {
        if (linerViewModel.detailLinerInfo.get() == null) {
            return;
        }
        view.openTell(linerViewModel.detailLinerInfo.get().getTell());
    }

    /**
     * ブラウザを起動
     */
    public void startWeb() {
        if (linerViewModel.detailLinerInfo.get() == null) {
            return;
        }
        view.openBrowser(linerViewModel.detailLinerInfo.get().getUrl());
    }
}
