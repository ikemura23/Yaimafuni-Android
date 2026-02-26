package com.yaeyama.linerchecker.ui.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yaeyama_liner_checker.domain.common.UiState
import com.yaeyama_liner_checker.domain.repository.WeatherRepository
import com.yaeyama_liner_checker.domain.weather.WeatherInfo
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

/**
 * 天気詳細 ViewModel
 */
class WeatherViewModel(
    private val weatherRepository: WeatherRepository,
) : ViewModel() {

    val weatherFlow: StateFlow<WeatherUiState> = weatherRepository.fetchWeather()
        .map { uiState ->
            when (uiState) {
                is UiState.Error -> WeatherUiState.Success(WeatherInfo())
                is UiState.Success -> WeatherUiState.Success(uiState.data)
                else -> WeatherUiState.Loading
            }
        }
        .onStart { emit(WeatherUiState.Loading) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = WeatherUiState.Loading,
        )
}
