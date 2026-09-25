package com.yaeyama.linerchecker.domain.statusdetail

/**
 * ある会社が運航する1航路の運航状況
 *
 * Firebase からデシリアライズするため、プロパティ名は Firebase のキーと一致させること。
 */
data class PortStatus(
    /** 運航状況の補足（例: 「海上時化の為、全便欠航。」）。無い場合は空文字 */
    val comment: String = "",
    /** 航路のコード（例: `"taketomi"`） */
    val portCode: String = "",
    /** 航路名（例: 「竹富航路」） */
    val portName: String = "",
    /** 運航状況 */
    val status: Status = Status(),
)
