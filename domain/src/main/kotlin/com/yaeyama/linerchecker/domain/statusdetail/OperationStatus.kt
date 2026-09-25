package com.yaeyama.linerchecker.domain.statusdetail

/**
 * 運航状況の区分
 */
enum class OperationStatus {
    /** 通常運航 */
    NORMAL,

    /** 未定・一部欠航など、注意が必要な状態 */
    CAUTION,

    /** 欠航 */
    CANCEL,
}

/**
 * Firebase のステータスコードを運航状況の区分に変換する
 *
 * - "nomal" はデータ元の表記ゆれのため "normal" と同じ扱いにする
 * - 不明なコードは、利用者が誤って通常運航と判断しないよう [OperationStatus.CAUTION] とする
 * - コードが空の場合（その会社が運航していない航路など）は null を返す
 */
fun Status.toOperationStatus(): OperationStatus? = when (code) {
    "" -> null
    "normal", "nomal" -> OperationStatus.NORMAL
    "cancel" -> OperationStatus.CANCEL
    else -> OperationStatus.CAUTION
}

/**
 * 表示できる運航状況があるか
 */
val Status.hasOperationStatus: Boolean get() = toOperationStatus() != null
