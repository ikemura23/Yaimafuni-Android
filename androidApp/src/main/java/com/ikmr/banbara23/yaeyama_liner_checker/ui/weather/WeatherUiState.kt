package com.ikmr.banbara23.yaeyama_liner_checker.ui.weather

import com.yaeyama_liner_checker.domain.weather.WeatherInfo

/**
 * 天気画面の状態を定義したState
 */
sealed interface WeatherUiState {
    object Loading : WeatherUiState

    data class Success(
        val weather: WeatherInfo,
    ) : WeatherUiState
}
