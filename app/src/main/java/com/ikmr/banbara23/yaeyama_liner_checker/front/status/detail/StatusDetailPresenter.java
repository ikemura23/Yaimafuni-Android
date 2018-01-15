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
                            }

                            @Override
                            public void onComplete() {
                                hideDialog();
                            }
                        })

//                        .subscribeWith(new DisposableSingleObserver<StatusDetailRoot>() {
//                            @Override
//                            public void onSuccess(StatusDetailRoot root) {
//
//                                // 運行のステータス + ステータス文字の背景色
//                                setViewModel(root.getPortStatus());
//
//                                // 運行関連情報（値段や時間）
//                                setViewModelOfLinerInfo(root.getDetailLinerInfo());
//
//                                // 時間別の運行ステータス
//                                setTimeTableViewModel(root.getTimeTable());
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//                                timeTableViewModel.canShow.set(false);
//                                e.printStackTrace();
//                            }
//                        })
        );
//        loadPortDetail();
//        loadDetailLinerInfo();
//        loadTimeTable();
    }

    public void onStop() {
        mDisposable.clear();
    }

    /**
     * 運行情報
     */
    protected void loadPortDetail() {
//        mDisposable.add(
//                new ApiClient().getStatusDetail(getTablePath())
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribeWith(new DisposableSingleObserver<PortStatus>() {
//                            @Override
//                            public void onSuccess(@NonNull PortStatus portStatus) {
//                                setViewModel(portStatus);
//                            }
//
//                            @Override
//                            public void onError(@NonNull Throwable e) {
//                            }
//                        })
//        );
    }

    /**
     * 運行ステータス以外の情報
     */
    protected void loadDetailLinerInfo() {
//        mDisposable.add(
//                new ApiClient().getDetailLinerInfo(getDetailLinerInfoPath())
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribeWith(new DisposableSingleObserver<DetailLinerInfo>() {
//                            @Override
//                            public void onSuccess(@NonNull DetailLinerInfo detailLinerInfo) {
//                                setViewModelOfLinerInfo(detailLinerInfo);
//                            }
//
//                            @Override
//                            public void onError(@NonNull Throwable e) {
//                            }
//                        })
//        );
    }

    /**
     * タイムテーブル
     */
    private void loadTimeTable() {
//        String path = company.getCode() + "_timeTable/" + portCode;
//        mDisposable.add(
//                new ApiClient().getTimeTable(path)
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribeWith(new DisposableSingleObserver<TimeTable>() {
//                            @Override
//                            public void onSuccess(@NonNull TimeTable timeTable) {
//                                setTimeTableViewModel(timeTable);
//                                Log.d(TAG, timeTable.toString());
//                            }
//
//                            @Override
//                            public void onError(@NonNull Throwable e) {
//                                timeTableViewModel.canShow.set(false);
//                            }
//                        })
//        );
    }

    private void setTimeTableViewModel(TimeTable timeTable) {

        if (timeTable.getHeader() != null) {
            timeTableViewModel.header.set(timeTable.getHeader());
        }
        if (timeTable.getRow() != null || !timeTable.getRow().isEmpty()) {
            timeTableViewModel.rows.clear();
            timeTableViewModel.rows.addAll(timeTable.getRow());
        }
        timeTableViewModel.canShow.set(true);
    }

    private String getDetailLinerInfoPath() {
        return company.getCode() + "_liner_info/" + portCode;
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

    private String getTablePath() {
        return company.getCode() + "/" + portCode;
    }

    /**
     * 外部電話アプリを起動
     */
    public void startTel() {
        view.openTell(linerViewModel.detailLinerInfo.get().getTell());
    }

    /**
     * ブラウザを起動
     */
    public void startWeb() {
        view.openBrowser(linerViewModel.detailLinerInfo.get().getUrl());
    }
}
