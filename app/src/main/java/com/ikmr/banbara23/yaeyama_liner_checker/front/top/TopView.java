package com.ikmr.banbara23.yaeyama_liner_checker.front.top;

import com.ikmr.banbara23.yaeyama_liner_checker.front.base.BaseView;
import com.ikmr.banbara23.yaeyama_liner_checker.model.Company;

/**
 * 港詳細のViewインターフェイス
 */
public interface TopView extends BaseView {

    // 安栄・八重山観光フェリー・ドリーム
    void onClickedCompany(Company company);

    // 時刻表
    void onClickedTimeTable();

    // 設定
    void onClickedSetting();
}
