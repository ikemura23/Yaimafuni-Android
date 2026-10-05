package com.yaeyama.linerchecker.ui.typhoon.detail

import kotlinx.serialization.Serializable

/**
 * 台風詳細画面に表示するデータ
 * Navigation 3 の NavKey（[com.yaeyama.linerchecker.ui.navigation.TyphoonDetail]）に持たせて保存・復元するため Serializable にする
 */
@Serializable
data class TyphoonDetailUiModel(
    /** 名前 */
    val name: String = "",
    /** 更新日 */
    val dateTime: String = "",
    /** 画像 */
    val img: String = "",
    /** 大きさ */
    val scale: String = "",
    /** 強さ */
    val intensity: String = "",
    /** 気圧 */
    val pressure: String = "",
    /** 存在地域 */
    val area: String = "",
    /** 中心の最大風速 */
    val maxWindSpeedNearCenter: String = "",
)
