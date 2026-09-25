package com.yaeyama.linerchecker.ui.weather

import androidx.annotation.StringRes
import com.yaeyama.linerchecker.domain.weather.WeatherInfo

/**
 * 天気画面の状態を定義したState
 */
sealed interface WeatherUiState {
    object Loading : WeatherUiState

    data class Error(
        @StringRes val messageRes: Int,
    ) : WeatherUiState

    data class Success(
        val weather: WeatherInfo,
    ) : WeatherUiState
}
