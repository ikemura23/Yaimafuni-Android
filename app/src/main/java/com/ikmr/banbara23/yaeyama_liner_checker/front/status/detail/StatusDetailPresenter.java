package com.ikmr.banbara23.yaeyama_liner_checker.front.status.detail;

import android.util.Log;

import com.ikmr.banbara23.yaeyama_liner_checker.api.ApiClient;
import com.ikmr.banbara23.yaeyama_liner_checker.front.base.Presenter;
import com.ikmr.banbara23.yaeyama_liner_checker.model.Company;
import com.ikmr.banbara23.yaeyama_liner_checker.model.PortStatus;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

/**
 * 運行詳細 Presenter
 */
public class StatusDetailPresenter implements Presenter<StatusDetailView> {
    private StatusDetailViewModel viewModel;
    private StatusDetailView view;
    private Company company;
    private String portCode;
    private CompositeDisposable mDisposable = new CompositeDisposable();

    StatusDetailPresenter(StatusDetailViewModel viewModel, Company company, String portCode) {
        this.viewModel = viewModel;
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

    protected void loadPortDetail() {
        mDisposable.add(
                new ApiClient().getStatusDetail(getTablePath())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<PortStatus>() {
                            @Override
                            public void onSuccess(@NonNull PortStatus portStatus) {
                                setViewModel(portStatus);
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                            }
                        })
        );
    }

    /**
     * ViewModelにセット
     *
     * @param portStatus
     */
    private void setViewModel(PortStatus portStatus) {
        Log.d(TAG, portStatus.toString());
        // ViewModelにセット
        viewModel.portStatus.set(portStatus);
        // 画像セット
        viewModel.setStatusDrawable(portStatus.getStatus().getCode());
    }

    private String getTablePath() {
        return company.getCode() + "/" + portCode;
    }

    /**
     * 外部電話アプリを起動
     */
    public void startTel() {
        view.openTell("00000");
    }

    /**
     * 外部ブラウザを起動
     */
    public void startWeb() {
        view.openBrowser("");
    }
}
