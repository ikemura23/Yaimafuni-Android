package com.yaeyama.linerchecker.domain.repository

import com.yaeyama.linerchecker.domain.typhoon.Typhoon
import kotlinx.coroutines.flow.Flow

/**
 * 台風情報を取得する Repository
 */
interface TyphoonRepository {

    /**
     * 発生中の台風の一覧を取得する
     *
     * 台風が発生していない場合は空リストを流す。購読している間は更新のたびに値を流し、
     * 失敗時は [com.yaeyama.linerchecker.domain.common.DataException] で Flow を終了する。
     */
    fun fetchTyphoonList(): Flow<List<Typhoon>>
}
