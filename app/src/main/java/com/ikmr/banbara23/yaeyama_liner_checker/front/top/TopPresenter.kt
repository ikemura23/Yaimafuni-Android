package com.ikmr.banbara23.yaeyama_liner_checker.front.top

import android.util.Log
import androidx.core.content.ContextCompat
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import com.ikmr.banbara23.yaeyama_liner_checker.R
import com.ikmr.banbara23.yaeyama_liner_checker.api.ApiClient
import com.ikmr.banbara23.yaeyama_liner_checker.front.base.Presenter
import com.ikmr.banbara23.yaeyama_liner_checker.model.Company
import com.ikmr.banbara23.yaeyama_liner_checker.model.Typhoon
import com.ikmr.banbara23.yaeyama_liner_checker.model.top.TopCompanyInfo
import com.ikmr.banbara23.yaeyama_liner_checker.model.top.TopPort
import com.ikmr.banbara23.yaeyama_liner_checker.model.weather.WeatherInfo
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subscribers.ResourceSubscriber

/**
 * トップ画面のPresenter
 * @param view
 * @param viewModel
 */
class TopPresenter(
    private var view: TopView?,
    private val viewModel: TopViewModel
) : Presenter<TopView> {
    private val mApiClient: ApiClient = ApiClient()
    private val mDisposable = CompositeDisposable()

    override fun attachView(view: TopView) {
        this.view = view
    }

    override fun detachView() {
        mDisposable.clear()
        view = null
    }

    fun onResume() {
        fetchCompanyStatus()
        fetchPortStatus()
        fetchWeather()
        fetchTyphoon()
    }

    private fun fetchPortStatus() {
        viewModel.showPortProgress.set(true)
        mDisposable.add(
            mApiClient
                .topPortStatus
                .subscribeWith(
                    object : ResourceSubscriber<TopPort>() {
                        override fun onNext(topPort: TopPort) {
                            Log.d("TopPresenter", "topPort:$topPort")
                            bindTopPort(topPort)
                            viewModel.showPortProgress.set(false)
                        }

                        override fun onError(t: Throwable) {
                            viewModel.showPortProgress.set(false)
                        }

                        override fun onComplete() {
                            viewModel.showPortProgress.set(false)
                        }
                    })
        )
    }

    private fun bindTopPort(topPort: TopPort) {
        viewModel.topPort.set(topPort)
    }

    /**
     * トップ用ステータスを取得
     */
    private fun fetchCompanyStatus() {
        viewModel.showCompanyProgress.set(true)
        mDisposable.add(
            mApiClient
                .topCompany
                .subscribeWith(object : ResourceSubscriber<TopCompanyInfo>() {

                    override fun onNext(topCompanyInfo: TopCompanyInfo) {
                        bindData(topCompanyInfo)
                        viewModel.showCompanyProgress.set(false)
                    }

                    override fun onError(t: Throwable) {
                        viewModel.showCompanyProgress.set(false)
                    }

                    override fun onComplete() {
                        viewModel.showCompanyProgress.set(false)
                    }
                })
        )
    }

    /**
     * 天気を取得
     */
    private fun fetchWeather() {
        viewModel.showWeatherProgress.set(true)
        mDisposable.add(
            mApiClient
                .weather
                .subscribeWith(
                    object : ResourceSubscriber<WeatherInfo>() {
                        override fun onNext(weatherInfo: WeatherInfo) {
                            onCompleteFromWeather(weatherInfo)
                            viewModel.showWeatherProgress.set(false)
                        }

                        override fun onError(t: Throwable) {
                            viewModel.showWeatherProgress.set(false)
                        }

                        override fun onComplete() {
                            viewModel.showWeatherProgress.set(false)
                        }
                    })
        )
    }

    /**
     * 台風を取得
     */
    private fun fetchTyphoon() {
        viewModel.showTyphoonProgress.set(true)
        mDisposable.add(
            mApiClient
                .typhoon
                .subscribeWith(
                    object : ResourceSubscriber<List<Typhoon>>() {
                        override fun onNext(typhoonList: List<Typhoon>) {
                            Log.d("fetchTyphoon", typhoonList.toString())
                            bindTyphoon(typhoonList)
                            viewModel.showTyphoonProgress.set(false)
                        }

                        override fun onError(t: Throwable) {
                            Log.d("fetchTyphoon", t.toString())
                            viewModel.showTyphoonProgress.set(false)
                        }

                        override fun onComplete() {
                            viewModel.showTyphoonProgress.set(false)
                        }
                    })
        )
    }

    /**
     * 台風の表示
     */
    private fun bindTyphoon(typhoonList: List<Typhoon>) {
        val text = when (typhoonList.size) {
            0 -> "発生しておりません。"
            1 -> typhoonList.first().name
            else -> createTyhoonText(typhoonList)
        }
        viewModel.typhoon.set(text)
    }

    private fun createTyhoonText(typhoonList: List<Typhoon>): String =
        typhoonList.map { it.name }.reduce { ty1, ty2 -> "$ty1, $ty2" }

    /**
     * 通信が完了
     *
     * @param topCompanyInfo
     */
    private fun bindData(topCompanyInfo: TopCompanyInfo) {

        setStatus(topCompanyInfo.anei, viewModel.aneiStatus, viewModel.aneiColor)
        setStatus(topCompanyInfo.ykf, viewModel.ykfStatus, viewModel.ykfColor)
    }

    /**
     * 天気APIリクエスト完了
     *
     * @param weatherInfo
     */
    private fun onCompleteFromWeather(weatherInfo: WeatherInfo) {
        if (weatherInfo.today == null) {
            return
        }
        Log.d("TopPresenter", weatherInfo.today.toString())
        //日付
        viewModel.date.set(weatherInfo.today.date)
        //天気＋波＋風速
        val weather = (weatherInfo.today.weather
            + " " +
            weatherInfo.today.wind +
            " 波" +
            weatherInfo.today.wave)
        viewModel.weather.set(weather)
    }

    /**
     * ステータス値からTextViewの文字・色をセットする
     *
     * @param company
     * @param statusText
     * @param colorInt
     */
    private fun setStatus(company: TopCompanyInfo.TopCompany, statusText: ObservableField<String>, colorInt: ObservableInt) {
        val status: String
        val color: Int

        when {
            company.cancel > 0 -> {
                status = "欠航あり"
                color = ContextCompat.getColor(getContext(), R.color.status_cancel)
            }
            company.suspend > 0 -> {
                status = "運休あり"
                color = ContextCompat.getColor(getContext(), R.color.status_cancel)
            }
            company.cation > 0 -> {
                status = "注意あり"
                color = ContextCompat.getColor(getContext(), R.color.status_cation)
            }
            else -> {
                status = "通常運行"
                color = ContextCompat.getColor(getContext(), R.color.status_normal)
            }
        }
        // ステータス文字
        statusText.set(status)
        // 文字色
        colorInt.set(color)
    }

    fun getContext() = view!!.getContext()

    //イベントハンドリング

    fun onClickWeather() {
        view!!.navigateToWeather()
    }

    fun onClickPort(portName: String, portCode: String) {
        view!!.navigateToPortStatusList(portName, portCode)
    }

    fun onClickCompany(company: Company) {
        view!!.navigateToCompanyStatusList(company)
    }

    fun onClickTyphoon() {
        view?.navigateToTyphoon()
    }
}
