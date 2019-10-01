package com.ikmr.banbara23.yaeyama_liner_checker.front.top

import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.databinding.ObservableInt

import com.ikmr.banbara23.yaeyama_liner_checker.model.top.TopPort

/**
 * Top画面のViewModel
 */
class TopViewModel {

    // 港別ステータス
    var topPort = ObservableField<TopPort>()
    var showPortProgress = ObservableBoolean()

    // 天気
    var date = ObservableField<String>()
    var weather = ObservableField<String>()
    var showWeatherProgress = ObservableBoolean()

    // 会社別ステータス
    var aneiColor = ObservableInt()
    var ykfColor = ObservableInt()

    var aneiStatus = ObservableField<String>()
    var ykfStatus = ObservableField<String>()

    var showCompanyProgress = ObservableBoolean()

    // 台風
    var typhoon = ObservableField<String>()
    var showTyphoonProgress = ObservableBoolean()
}
