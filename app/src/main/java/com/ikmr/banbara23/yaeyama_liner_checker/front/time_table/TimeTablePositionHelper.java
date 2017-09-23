
package com.ikmr.banbara23.yaeyama_liner_checker.front.time_table;

import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.core.Base;
import com.ikmr.banbara23.yaeyama_liner_checker.model.Company;
import com.ikmr.banbara23.yaeyama_liner_checker.utils.PreferenceUtils;

/**
 * 時刻表のタブ位置、スピナー位置を記憶、呼び出すヘルパークラス
 */
public class TimeTablePositionHelper {

    /**
     * 選択タブインデックスを取得
     *
     * @return 選択すべきタブ番号
     */
    public static int getInitTabPosition() {
        return PreferenceUtils.loadInt(Base.getContext().getString(R.string.time_table_init_tab_value));
    }

    /**
     * タブインデックスを記憶
     *
     * @param currentPosition 現在のタブ位置
     */
    public static void setCurrentTabPosition(int currentPosition) {
        PreferenceUtils.saveInt(Base.getContext().getString(R.string.time_table_init_tab_value), currentPosition);
    }

    /**
     * 港スピナーのインデックスを取得
     *
     * @return
     */
    public static int getInitSpinnerPosition(Company company) {
        String key = null;
        switch (company) {
            case ANNEI:
                key = Base.getContext().getString(R.string.time_table_annei_init_spinner_value);
                break;
            case YKF:
                key = Base.getContext().getString(R.string.time_table_ykf_init_spinner_value);
                break;
            case DREAM:
                key = Base.getContext().getString(R.string.time_table_dream_init_spinner_value);
                break;
        }
//        Timber.d("TimeTablePositionHelper", "getInitSpinnerPosition : " + company.getCompanyName() + " - " + PreferenceUtils.loadInt(key));
        return PreferenceUtils.loadInt(key);
    }

    /**
     * 港スピナーのインデックスを保存
     *
     * @param currentPosition
     */
    public static void setCurrentSpinnerPosition(Company company, int currentPosition) {
        String key = null;
        switch (company) {
            case ANNEI:
                key = Base.getContext().getString(R.string.time_table_annei_init_spinner_value);
                break;
            case YKF:
                key = Base.getContext().getString(R.string.time_table_ykf_init_spinner_value);
                break;
            case DREAM:
                key = Base.getContext().getString(R.string.time_table_dream_init_spinner_value);
                break;
        }
//        Timber.d("TimeTablePositionHelper", "setCurrentSpinnerPosition : " + company.getCompanyName() + " - " + currentPosition);
        PreferenceUtils.saveInt(key, currentPosition);
    }
}
