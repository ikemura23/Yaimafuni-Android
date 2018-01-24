package com.ikmr.banbara23.yaeyama_liner_checker.front.weather;

import android.app.Dialog;
import android.support.v7.app.AlertDialog;

import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.api.ApiClient;
import com.ikmr.banbara23.yaeyama_liner_checker.front.base.Presenter;
import com.ikmr.banbara23.yaeyama_liner_checker.model.weather.WeatherInfo;
import com.ikmr.banbara23.yaeyama_liner_checker.model.weather.WeatherViewModel;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableMaybeObserver;

public class WeatherPresenter implements Presenter<WeatherView> {
    private WeatherView mView;
    private WeatherViewModel mToday;
    private WeatherViewModel mTomorrow;
    private CompositeDisposable mDisposable = new CompositeDisposable();

    public WeatherPresenter(WeatherViewModel today, WeatherViewModel tomorrow) {
        mToday = today;
        mTomorrow = tomorrow;
    }

    @Override
    public void attachView(WeatherView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        if (mDisposable != null) {
            mDisposable.clear();
        }
        mView = null;
    }

    public void onResume() {
        fetchWeather();
    }

    /**
     * 天気を取得
     */
    protected void fetchWeather() {
        showDialog();
        mDisposable.add(
                new ApiClient().getWeather()
                        .subscribeWith(
                                new DisposableMaybeObserver<WeatherInfo>() {
                                    @Override
                                    public void onSuccess(WeatherInfo weatherInfo) {
                                        bindData(weatherInfo);
                                        hideDialog();
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        hideDialog();
                                    }

                                    @Override
                                    public void onComplete() {

                                    }
                                })
        );
    }

    /**
     * 天気データをViewModelにセット
     *
     * @param weatherInfo
     */
    private void bindData(WeatherInfo weatherInfo) {
        mToday.weather.set(weatherInfo.getToday());
        mTomorrow.weather.set(weatherInfo.getTomorrow());
    }

    public void onButtonClick() {
        mView.openBrowser();
    }

    private Dialog mDialog;

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mView.getContext());
        builder.setView(R.layout.progress);
        mDialog = builder.create();
        mDialog.show();
    }

    private void hideDialog() {
        mDialog.dismiss();
    }
}
