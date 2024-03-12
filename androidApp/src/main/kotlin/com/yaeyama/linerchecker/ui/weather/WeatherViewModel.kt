package com.yaeyama.linerchecker.ui.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yaeyama.linerchecker.repository.UiState
import com.yaeyama.linerchecker.repository.WeatherRepository
import com.yaeyama_liner_checker.domain.weather.WeatherInfo
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * 天気詳細 ViewModel
 */
class WeatherScreenViewModel : ViewModel(), KoinComponent {

    private val weatherRepository: WeatherRepository by inject()

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
