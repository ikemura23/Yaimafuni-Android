package com.ikmr.banbara23.yaeyama_liner_checker.model

import com.ikemura.shared.model.statusdetail.PortStatus
import com.ikmr.banbara23.yaeyama_liner_checker.model.time_table.TimeTable

/**
 * ステータス詳細を表示するルートモデル
 *
 *
 * RxJavaのzipで使うために作成した
 */
data class StatusDetailRoot(
    val portStatus: PortStatus,
    val detailLinerInfo: DetailLinerInfo,
    val timeTable: TimeTable
)
