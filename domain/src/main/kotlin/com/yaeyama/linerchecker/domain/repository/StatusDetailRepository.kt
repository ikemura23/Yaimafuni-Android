package com.yaeyama.linerchecker.domain.repository

import com.yaeyama.linerchecker.domain.statusdetail.Company
import com.yaeyama.linerchecker.domain.statusdetail.PortStatus
import com.yaeyama.linerchecker.domain.timetable.TimeTable
import kotlinx.coroutines.flow.Flow

/**
 * 航路ごとの運航詳細と時刻表を取得する Repository
 *
 * どちらのメソッドも購読している間は更新のたびに値を流し、
 * 失敗時は [com.yaeyama.linerchecker.domain.common.DataException] で Flow を終了する。
 */
interface StatusDetailRepository {

    /**
     * [company] が運航する [portCode] の航路の運航状況を取得する
     *
     * @param portCode 航路のコード（例: `"taketomi"`）
     */
    fun fetchStatusDetail(company: Company, portCode: String): Flow<PortStatus>

    /**
     * [company] が運航する [portCode] の航路の時刻表を取得する
     *
     * @param portCode 航路のコード（例: `"taketomi"`）
     */
    fun fetchTimeTable(company: Company, portCode: String): Flow<TimeTable>
}
