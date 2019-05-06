package com.ikmr.banbara23.yaeyama_liner_checker.front.weather

import android.app.Dialog
import android.support.v7.app.AlertDialog

import com.ikmr.banbara23.yaeyama_liner_checker.R
import com.ikmr.banbara23.yaeyama_liner_checker.api.ApiClient
import com.ikmr.banbara23.yaeyama_liner_checker.front.base.Presenter
import com.ikmr.banbara23.yaeyama_liner_checker.model.weather.WeatherInfo
import com.ikmr.banbara23.yaeyama_liner_checker.model.weather.WeatherViewModel

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subscribers.ResourceSubscriber

class WeatherPresenter(
    private val mToday: WeatherViewModel,
    private val mTomorrow: WeatherViewModel
) : Presenter<WeatherView> {
    private var mView: WeatherView? = null
    private val mDisposable = CompositeDisposable()

    private var mDialog: Dialog? = null

    override fun attachView(view: WeatherView) {
        mView = view
    }

    override fun detachView() {
        mDisposable.clear()
        mView = null
    }

    fun onResume() {
        fetchWeather()
    }

    /**
     * 天気を取得
     */
    protected fun fetchWeather() {
        showDialog()
        mDisposable.add(
            ApiClient().weather
                    .subscribeWith(
                        object : ResourceSubscriber<WeatherInfo>() {
                            override fun onNext(weatherInfo: WeatherInfo) {
                                bindData(weatherInfo)
                                hideDialog()
                            }

                            override fun onError(e: Throwable) {
                                hideDialog()
                            }

                            override fun onComplete() {
                            }
                        })
        )
    }

    /**
     * 天気データをViewModelにセット
     *
     * @param weatherInfo
     */
    private fun bindData(weatherInfo: WeatherInfo) {
        mToday.weather.set(weatherInfo.today)
        mTomorrow.weather.set(weatherInfo.tomorrow)
    }

    fun onButtonClick() {
        mView?.openBrowser()
    }

    private fun showDialog() {
        val builder = AlertDialog.Builder(mView!!.getContext())
        builder.setView(R.layout.progress)
        mDialog = builder.create()
        mDialog?.show()
    }

    private fun hideDialog() {
        mDialog?.dismiss()
    }
}
