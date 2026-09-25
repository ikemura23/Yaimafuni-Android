package com.yaeyama.linerchecker.domain.statusdetail

/**
 * 運航状況
 *
 * [code] の解釈は [toOperationStatus] に集約している。
 */
data class Status(
    /** ステータスコード（`"normal"` / `"cation"` / `"cancel"` など）。運航していない場合は空文字 */
    val code: String = "",
    /** 表示用のテキスト（例: 「通常運航」） */
    val text: String = "",
)
