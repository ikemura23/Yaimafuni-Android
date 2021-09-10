package com.ikmr.banbara23.yaeyama_liner_checker.ui.typhoon.list

import androidx.lifecycle.ViewModel
import com.ikmr.banbara23.yaeyama_liner_checker.repository.TyphoonRepositoryOld
import com.ikmr.banbara23.yaeyama_liner_checker.repository.TyphoonUiState
import kotlinx.coroutines.flow.Flow

/**
 * 台風一覧 ViewModel
 */
class TyphoonListViewModel constructor(
    private val typhoonRepositoryOld: TyphoonRepositoryOld,
) : ViewModel() {
    /**
     * 台風一覧を取得する
     */
    fun getTyphoonList(): Flow<TyphoonUiState> = typhoonRepositoryOld.getTyphoonList()
}
