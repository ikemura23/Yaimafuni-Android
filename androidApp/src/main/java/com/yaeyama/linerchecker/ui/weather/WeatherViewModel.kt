package com.yaeyama.linerchecker.ui.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yaeyama_liner_checker.domain.common.UiState
import com.yaeyama_liner_checker.domain.repository.WeatherRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

/**
 * 天気詳細 ViewModel
 */
@OptIn(ExperimentalCoroutinesApi::class)
class WeatherViewModel(
    private val weatherRepository: WeatherRepository,
) : ViewModel() {

    private val retryTrigger = MutableSharedFlow<Unit>(extraBufferCapacity = 1)

    val weatherFlow: StateFlow<WeatherUiState> = retryTrigger
        .onStart { emit(Unit) }
        .flatMapLatest {
            weatherRepository.fetchWeather()
                .map { uiState ->
                    when (uiState) {
                        is UiState.Error -> WeatherUiState.Error
                        is UiState.Success -> WeatherUiState.Success(uiState.data)
                        is UiState.Loading -> WeatherUiState.Loading
                    }
                }
                .onStart { emit(WeatherUiState.Loading) }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = WeatherUiState.Loading,
        )

    fun retry() {
        retryTrigger.tryEmit(Unit)
    }
}
