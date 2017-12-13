package com.ikmr.banbara23.yaeyama_liner_checker.front.weather;

import com.ikmr.banbara23.yaeyama_liner_checker.api.ApiClient;
import com.ikmr.banbara23.yaeyama_liner_checker.front.base.Presenter;
import com.ikmr.banbara23.yaeyama_liner_checker.model.weather.WeatherInfo;
import com.ikmr.banbara23.yaeyama_liner_checker.model.weather.WeatherViewModel;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

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
        fetchWeather();
    }

    @Override
    public void detachView() {
        if (mDisposable != null) {
            mDisposable.clear();
        }
        mView = null;
    }

    /**
     * 天気を取得
     */
    protected void fetchWeather() {
        mDisposable.add(
                new ApiClient().getWeather()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<WeatherInfo>() {
                            @Override
                            public void onSuccess(@NonNull WeatherInfo weatherInfo) {
                                bindData(weatherInfo);
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
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
}
