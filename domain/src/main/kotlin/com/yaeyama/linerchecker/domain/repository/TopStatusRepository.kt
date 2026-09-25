package com.yaeyama.linerchecker.domain.repository

import com.yaeyama.linerchecker.domain.top.TopPort
import kotlinx.coroutines.flow.Flow

/**
 * トップ画面用の運航ステータスを取得する Repository
 */
interface TopStatusRepository {

    /**
     * 全航路の会社別の運航状況を取得する
     *
     * 購読している間は更新のたびに値を流し、失敗時は
     * [com.yaeyama.linerchecker.domain.common.DataException] で Flow を終了する。
     * 表示順への並べ替えは [com.yaeyama.linerchecker.domain.usecase.GetTopStatuses] が行う。
     */
    fun fetchTopStatuses(): Flow<TopPort>
}
