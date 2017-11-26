package com.ikmr.banbara23.yaeyama_liner_checker.front.top;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.v4.content.ContextCompat;

import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.api.ApiClient;
import com.ikmr.banbara23.yaeyama_liner_checker.front.base.Presenter;
import com.ikmr.banbara23.yaeyama_liner_checker.model.TopCompanyInfo;
import com.ikmr.banbara23.yaeyama_liner_checker.model.weather.WeatherInfo;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * トップ画面のPresenter
 */
public class TopPresenter implements Presenter<TopView> {
    private TopViewModel viewModel;
    private TopView view;
    private ApiClient mApiClient;
    private DisposableSingleObserver<WeatherInfo> mDisposableSingleObserver = null;
    private CompositeDisposable mDisposable = new CompositeDisposable();

    /**
     * コンストラクタ
     *
     * @param view
     * @param viewModel
     */
    TopPresenter(TopView view, TopViewModel viewModel) {
        this.view = view;
        this.viewModel = viewModel;

        mApiClient = new ApiClient();
    }

    @Override
    public void attachView(TopView view) {
        this.view = view;
        view.showProgressBar();
    }

    @Override
    public void detachView() {
        mDisposable.clear();
        view = null;
    }

    public void onResume() {
        fetchTopStatus();
        fetchWeather();
    }

    /**
     * トップ用ステータスを取得
     */
    protected void fetchTopStatus() {
        mDisposable.add(
                mApiClient.getTopCompany()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<TopCompanyInfo>() {
                            @Override
                            public void onSuccess(@NonNull TopCompanyInfo topCompanyInfo) {
                                onComplete(topCompanyInfo);
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                view.hideProgressBar();
                            }
                        })
        );
    }

    /**
     * 天気を取得
     */
    protected void fetchWeather() {
        mDisposable.add(
                mApiClient.getWeather()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<WeatherInfo>() {
                            @Override
                            public void onSuccess(@NonNull WeatherInfo weatherInfo) {
                                onCompleteFromWeather(weatherInfo);
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                            }
                        })
        );
    }

    /**
     * 通信が完了
     *
     * @param topCompanyInfo
     */
    private void onComplete(TopCompanyInfo topCompanyInfo) {
        viewModel.topCompany.set(topCompanyInfo);

        setStatus(topCompanyInfo.getAnei(), viewModel.aneiStatus, viewModel.aneiColor);
        setStatus(topCompanyInfo.getYkf(), viewModel.ykfStatus, viewModel.ykfColor);
        setStatus(topCompanyInfo.getDream(), viewModel.dreamStatus, viewModel.dreamColor);

        view.hideProgressBar();
    }

    /**
     * 天気APIリクエスト完了
     *
     * @param weatherInfo
     */
    private void onCompleteFromWeather(WeatherInfo weatherInfo) {
        if (weatherInfo.getToday() == null) {
            return;
        }
        String weather = weatherInfo.getToday().getDate() +
                " " +
                weatherInfo.getToday().getWind() +
                " 波" +
                weatherInfo.getToday().getWave();
        viewModel.todayWeather.set(weather);
    }

    /**
     * ステータス値からTextViewの文字・色をセットする
     *
     * @param company
     * @param statusText
     * @param colorInt
     */
    private void setStatus(TopCompanyInfo.TopCompany company, ObservableField<String> statusText, ObservableInt colorInt) {
        String status;
        int color;
        if (company.getCancel() > 0) {
            status = "欠航あり";
            color = ContextCompat.getColor(view.getContext(), R.color.status_cancel);
        } else if (company.getSuspend() > 0) {
            status = "運休あり";
            color = ContextCompat.getColor(view.getContext(), R.color.status_cancel);
        } else if (company.getCation() > 0) {
            status = "注意あり";
            color = ContextCompat.getColor(view.getContext(), R.color.status_cation);
        } else {
            status = "通常運行";
            color = ContextCompat.getColor(view.getContext(), R.color.status_normal);
        }
        // ステータス文字
        statusText.set(status);
        // 文字色
        colorInt.set(color);
    }
}