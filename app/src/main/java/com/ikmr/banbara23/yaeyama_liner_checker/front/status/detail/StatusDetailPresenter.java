package com.ikmr.banbara23.yaeyama_liner_checker.front.status.detail;

import android.app.Dialog;
import android.support.v7.app.AlertDialog;

import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.api.ApiClient;
import com.ikmr.banbara23.yaeyama_liner_checker.front.base.Presenter;
import com.ikmr.banbara23.yaeyama_liner_checker.model.Company;
import com.ikmr.banbara23.yaeyama_liner_checker.model.DetailLinerInfo;
import com.ikmr.banbara23.yaeyama_liner_checker.model.PortStatus;
import com.ikmr.banbara23.yaeyama_liner_checker.model.StatusDetailRoot;
import com.ikmr.banbara23.yaeyama_liner_checker.model.time_table.TimeTable;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

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
    private Dialog mDialog;

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

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setView(R.layout.progress);
        mDialog = builder.create();
        mDialog.show();
    }

    private void hideDialog() {
        mDialog.dismiss();
    }

    public void onResume() {
        showDialog();
        mDisposable.add(
                ApiClient.getDetailInfo(company, portCode)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<StatusDetailRoot>() {
                            @Override
                            public void onNext(StatusDetailRoot root) {
                                // 運行のステータス + ステータス文字の背景色
                                setViewModel(root.getPortStatus());

                                // 運行関連情報（値段や時間）
                                setViewModelOfLinerInfo(root.getDetailLinerInfo());

                                // 時間別の運行ステータス
                                setTimeTableViewModel(root.getTimeTable());
                            }

                            @Override
                            public void onError(Throwable e) {
                                timeTableViewModel.canShow.set(false);
                                e.printStackTrace();
                                hideDialog();
                            }

                            @Override
                            public void onComplete() {
                                hideDialog();
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
