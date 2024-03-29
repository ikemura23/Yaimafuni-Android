package com.yaeyama.linerchecker.ui.typhoon.detail

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
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
) : Parcelable
