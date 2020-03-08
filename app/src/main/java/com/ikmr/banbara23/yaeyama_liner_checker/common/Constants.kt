package com.ikmr.banbara23.yaeyama_liner_checker.common

/**
 * 定数クラス
 */
object Constants {

    // 天気 Webページ URL
    const val WEATHER_URL = "https://tenki.jp/forecast/10/50/9410/47207/"

    // 台風 Webページ URL
    const val TYPHOON_URL = "https://tenki.jp/lite/bousai/typhoon/"

    const val BUNDLE_KEY_DETAIL = "BUNDLE_KEY_DETAIL"
    const val BUNDLE_KEY_COMPANY = "BUNDLE_KEY_COMPANY"
    const val BUNDLE_KEY_PORT_CODE = "BUNDLE_KEY_PORT_CODE"
    const val BUNDLE_KEY_PORT_NAME = "BUNDLE_KEY_PORT_NAME"
}

// FireBase Analytics
object FireBaseAnalyticsTag {
    const val TOP = "top"
    const val OTHER = "other"
    const val SETTING = "setting"
    const val STATUS_LIST = "status_list"
    const val STATUS_DETAIL_ANNEI = "status_detail_annei"
    const val STATUS_DETAIL_YKF = "status_detail_ykf"
    const val STATUS_DETAIL_DREAM = "status_detail_dream"
    const val WEATHER = "weather"
    const val TIME_TABLE = "time_table"
    const val TIME_TABLE_SPINNER = "time_table_spinner"
}
