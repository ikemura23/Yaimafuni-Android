package com.ikmr.banbara23.yaeyama_liner_checker.front.top

import android.graphics.drawable.Drawable
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
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

    // ステータス文字
    var aneiStatus = ObservableField<String>()
    var ykfStatus = ObservableField<String>()

    // ステータスの背景色（丸角）
    var aneiBackground = ObservableField<Drawable>()
    var ykfBackground = ObservableField<Drawable>()

    // 読込中のプログレス表示
    var showCompanyProgress = ObservableBoolean()

    // 台風
    var typhoon = ObservableField<String>()
    var showTyphoonProgress = ObservableBoolean()
}
