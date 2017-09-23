
package com.ikmr.banbara23.yaeyama_liner_checker;

/**
 * 定数クラス
 */
public class Const {
    // 通信全般のタイムアウト 10秒, Jsoupのデフォルトは3秒
    // http://jsoup.org/apidocs/org/jsoup/Connection.html
    public static final int CONNECTION_TIME_OUT = 10000;

    // 一覧の値
    public static final String PREF_ANNEI_LIST_KEY = "PREF_ANNEI_LIST_KEY";
    public static final String PREF_YKF_LIST_KEY = "PREF_YKF_LIST_KEY";
    public static final String PREF_DREAM_LIST_KEY = "PREF_DREAM_LIST_KEY";
    public static final String PREF_ANNEI_DETAIL_KEY = "PREF_ANNEI_DETAIL_KEY";

    // 一覧のタイムスタンプ
    public static final String TIMESTAMP_ANNEI_LIST_KEY = "TIMESTAMP_ANNEI_LIST_KEY";
    public static final String TIMESTAMP_YKF_LIST_KEY = "TIMESTAMP_YKF_LIST_KEY";
    public static final String TIMESTAMP_DREAM_LIST_KEY = "TIMESTAMP_DREAM_LIST_KEY";

    // 設定画面で設定するキャッシュ設定値
    public static final String CACHE_CHECKBOX_PREFERENCE = "cache_checkbox_preference";

    // 詳細の値
    public static final String PREF_ANNEI_DETAIL_TAKETOMI_KEY = "PREF_ANNEI_DETAIL_TAKETOMI_KEY";
    public static final String PREF_ANNEI_DETAIL_KOHAMA_KEY = "PREF_ANNEI_DETAIL_KOHAMA_KEY";
    public static final String PREF_ANNEI_DETAIL_KUROSHIMA_KEY = "PREF_ANNEI_DETAIL_KUROSHIMA_KEY";
    public static final String PREF_ANNEI_DETAIL_OOHARA_KEY = "PREF_ANNEI_DETAIL_OOHARA_KEY";
    public static final String PREF_ANNEI_DETAIL_UEHARA_KEY = "PREF_ANNEI_DETAIL_UEHARA_KEY";
    public static final String PREF_ANNEI_DETAIL_HATOMA_KEY = "PREF_ANNEI_DETAIL_HATOMA_KEY";
    public static final String PREF_ANNEI_DETAIL_HATERUMA_KEY = "PREF_ANNEI_DETAIL_HATERUMA_KEY";

    // 詳細のタイムスタンプ
    public static final String TIMESTAMP_ANNEI_DETAIL_TAKETOMI_KEY = "TIMESTAMP_ANNEI_DETAIL_TAKETOMI_KEY";
    public static final String TIMESTAMP_ANNEI_DETAIL_KOHAMA_KEY = "TIMESTAMP_ANNEI_DETAIL_KOHAMA_KEY";
    public static final String TIMESTAMP_ANNEI_DETAIL_KUROSHIMA_KEY = "TIMESTAMP_ANNEI_DETAIL_KUROSHIMA_KEY";
    public static final String TIMESTAMP_ANNEI_DETAIL_OOHARA_KEY = "TIMESTAMP_ANNEI_DETAIL_OOHARA_KEY";
    public static final String TIMESTAMP_ANNEI_DETAIL_UEHARA_KEY = "TIMESTAMP_ANNEI_DETAIL_UEHARA_KEY";
    public static final String TIMESTAMP_ANNEI_DETAIL_HATOMA_KEY = "TIMESTAMP_ANNEI_DETAIL_HATOMA_KEY";
    public static final String TIMESTAMP_ANNEI_DETAIL_HATERUMA_KEY = "TIMESTAMP_ANNEI_DETAIL_HATERUMA_KEY";

    // キャッシュの保存時間(分)
    public static final int SAVE_TIME = 3;

    // FireBase Analytics
    public class FireBaseAnalitycsTag {
        public static final String TOP = "top";
        public static final String OTHER = "other";
        public static final String SETTING = "setting";
        public static final String STATUS_LIST = "status_list";
        public static final String STATUS_DETAIL_ANNEI = "status_detail_annei";
        public static final String STATUS_DETAIL_YKF = "status_detail_ykf";
        public static final String STATUS_DETAIL_DREAM = "status_detail_dream";
        public static final String WEATHER = "weather";
        public static final String TIME_TABLE = "time_table";
        public static final String TIME_TABLE_SPINNER = "time_table_spinner";
    }

    //テーブル名
    public class NcmbTable {
        public final static String ANEI_LIST_TABLE = "AneiLinerStatusList";
        public final static String ANEI_DETAIL_TABLE = "AneiLinerStatusDetail";
        public final static String TopInfo = "TopInfo";

    }

    //カラム名
    public class NcmbColumn {
        public final static String UPDATE_DATE = "updateDate";
        public final static String LINER_ID = "linerId";
        public final static String OBJECT_ID = "objectId";
        public final static String JSON = "json";
    }

    public class TopInfo {
        public final static String company_anei_status_type = "company_anei_status_type";
        public final static String company_ykf_status_type = "company_ykf_status_type";
        public final static String company_dream_status_type = "company_dream_status_type";
        public final static String port_taketomi_status_type = "port_taketomi_status_type";
        public final static String port_kohama_status_type = "port_kohama_status_type";
        public final static String port_kuroshima_status_type = "port_kuroshima_status_type";
        public final static String port_uehara_status_type = "port_uehara_status_type";
        public final static String port_oohara_status_type = "port_oohara_status_type";
        public final static String port_hatoma_status_type = "port_hatoma_status_type";
        public final static String port_hateruma_status_type = "port_hateruma_status_type";

    }

    public static final String weatherUrl = "http://weather.yahoo.co.jp/weather/jp/47/9410.html";
}
