package com.ikmr.banbara23.yaeyama_liner_checker.ui.typhoon.list

import androidx.lifecycle.ViewModel
import com.ikemura.shared.model.tyhoon.Typhoon
import com.ikemura.shared.repository.TyphoonRepository
import kotlinx.coroutines.flow.Flow

/**
 * 台風一覧 ViewModel
 */
class TyphoonListViewModel constructor(
    private val typhoonRepository: TyphoonRepository,
) : ViewModel() {
    /**
     * 台風一覧を取得する
     */
    fun getTyphoonList(): Flow<List<Typhoon>> = typhoonRepository.fetchTyphoonList()
}
