package com.yaeyama.linerchecker.domain.usecase

import com.yaeyama.linerchecker.domain.repository.TyphoonRepository
import com.yaeyama.linerchecker.domain.typhoon.Typhoon
import kotlinx.coroutines.flow.Flow

/**
 * 発生中の台風の一覧を取得する
 * 台風が発生していない場合は空リストを流す
 */
class GetTyphoonList(
    private val typhoonRepository: TyphoonRepository,
) {
    operator fun invoke(): Flow<List<Typhoon>> = typhoonRepository.fetchTyphoonList()
}
