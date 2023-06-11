package com.yaeyama.linerchecker.ui.main

import androidx.lifecycle.ViewModel
import com.yaeyama.linerchecker.repository.TyphoonRepositoryImpl
import com.yaeyama_liner_checker.domain.tyhoon.Typhoon
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
