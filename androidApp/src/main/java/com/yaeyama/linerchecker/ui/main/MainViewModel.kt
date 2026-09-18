package com.yaeyama.linerchecker.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yaeyama_liner_checker.domain.repository.TyphoonRepository
import com.yaeyama_liner_checker.domain.typhoon.Typhoon
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class MainViewModel(
    private val typhoonRepository: TyphoonRepository,
) : ViewModel() {

    val typhoonCount: StateFlow<Int> = typhoonRepository.fetchTyphoonList()
        .map { list: List<Typhoon> -> list.size }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = 0,
        )
}
