package com.ikmr.banbara23.yaeyama_liner_checker.ui.typhoon.list

import androidx.lifecycle.ViewModel
import com.ikmr.banbara23.yaeyama_liner_checker.repository.TyphoonRepository
import com.ikmr.banbara23.yaeyama_liner_checker.repository.TyphoonUiState
import kotlinx.coroutines.flow.Flow

/**
 * 台風
 */
class TyphoonListViewModel constructor(
    private val typhoonRepository: TyphoonRepository,
) : ViewModel() {
    // val uiState = MutableLiveData<TyphoonUiState>()
    fun getTyphoonList(): Flow<TyphoonUiState> {
        return typhoonRepository.getTyphoonList()
    }
}
