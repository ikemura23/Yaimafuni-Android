package com.ikmr.banbara23.yaeyama_liner_checker.front.top;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;

import com.ikmr.banbara23.yaeyama_liner_checker.model.TopCompanyInfo;

/**
 * Top画面のViewModel
 */
public class TopViewModel {
    // ステータス
    public ObservableField<TopCompanyInfo> topCompany = new ObservableField<>();

    // 天気
    public ObservableField<String> todayWeather = new ObservableField<>();

    ObservableInt aneiColor = new ObservableInt();
    ObservableInt ykfColor = new ObservableInt();
    ObservableInt dreamColor = new ObservableInt();

    ObservableField<String> aneiStatus = new ObservableField<>();
    ObservableField<String> ykfStatus = new ObservableField<>();
    ObservableField<String> dreamStatus = new ObservableField<>();
}
