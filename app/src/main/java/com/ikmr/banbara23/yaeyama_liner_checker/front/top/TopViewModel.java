package com.ikmr.banbara23.yaeyama_liner_checker.front.top;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;

import com.ikmr.banbara23.yaeyama_liner_checker.model.top.TopPort;

/**
 * Top画面のViewModel
 */
public class TopViewModel {
    // ステータス
//    public ObservableField<TopCompanyInfo> topCompany = new ObservableField<>();
    // 港別ステータス
    public ObservableField<TopPort> topPort = new ObservableField<>();
    public ObservableBoolean showPortProgress = new ObservableBoolean();

    // 天気
    public ObservableField<String> todayWeather = new ObservableField<>();
    public ObservableBoolean showWeatherProgres = new ObservableBoolean();

    // 会社別ステータス
    public ObservableInt aneiColor = new ObservableInt();
    public ObservableInt ykfColor = new ObservableInt();
    public ObservableInt dreamColor = new ObservableInt();

    public ObservableField<String> aneiStatus = new ObservableField<>();
    public ObservableField<String> ykfStatus = new ObservableField<>();
    public ObservableField<String> dreamStatus = new ObservableField<>();

    public ObservableBoolean showCompanyProgres = new ObservableBoolean();
}
