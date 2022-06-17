package com.ikmr.banbara23.yaeyama_liner_checker.ui.main

import androidx.lifecycle.ViewModel
import com.yaeyama_liner_checker.domain.tyhoon.Typhoon
import com.ikemura.shared.repository.TyphoonRepositoryImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MainViewModel : ViewModel() {
    private val typhoonRepository = TyphoonRepositoryImpl()

    fun existsTyphoon(): Flow<Int> {
        return typhoonRepository.fetchTyphoonList().map { list: List<Typhoon> ->
            list.size
        }
    }
}