package com.ikmr.banbara23.yaeyama_liner_checker.ui.typhoon.list

import androidx.lifecycle.ViewModel
import com.ikemura.shared.model.tyhoon.Typhoon
import com.ikemura.shared.repository.TyphoonRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * 台風一覧 ViewModel
 */
class TyphoonListViewModel : ViewModel(), KoinComponent {
    private val typhoonRepository: TyphoonRepository by inject()

    /**
     * 台風一覧を取得する
     */
    fun getTyphoonList(): Flow<List<Typhoon>> = typhoonRepository.fetchTyphoonList()
}
