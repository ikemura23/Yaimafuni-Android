package com.yaeyama.linerchecker.ui.navigation

import androidx.navigation3.runtime.NavKey
import com.yaeyama.linerchecker.ui.typhoon.detail.TyphoonDetailUiModel
import kotlinx.serialization.Serializable

/**
 * ボトムナビゲーションを持つホーム画面（運航状況・天気・台風の各タブ）
 */
@Serializable
data object Main : NavKey

/**
 * 港ごとの運航詳細画面
 * @param portCode 港コード
 * @param portName トップバーに表示する港名
 */
@Serializable
data class PortStatusDetail(
    val portCode: String,
    val portName: String,
) : NavKey

/**
 * 台風詳細画面
 * 台風データには安定したIDが無いため、表示に必要なデータを丸ごと渡す
 */
@Serializable
data class TyphoonDetail(
    val typhoon: TyphoonDetailUiModel,
) : NavKey
