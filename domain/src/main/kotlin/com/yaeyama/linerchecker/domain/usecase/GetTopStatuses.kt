package com.yaeyama.linerchecker.domain.usecase

import com.yaeyama.linerchecker.domain.repository.TopStatusRepository
import com.yaeyama.linerchecker.domain.top.Ports
import com.yaeyama.linerchecker.domain.top.TopPort
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * トップ画面に表示する航路ごとの運航状況を、表示順に並べて取得する
 */
class GetTopStatuses(
    private val topStatusRepository: TopStatusRepository,
) {
    operator fun invoke(): Flow<List<Ports>> =
        topStatusRepository.fetchTopStatuses().map { topPort -> topPort.toDisplayOrder() }
}

/**
 * 航路を表示順（石垣島から近い順、波照間は最後）に並べる
 */
internal fun TopPort.toDisplayOrder(): List<Ports> = listOf(
    taketomi,
    kohama,
    kuroshima,
    oohara,
    uehara,
    hatoma,
    hateruma,
)
