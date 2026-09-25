package com.yaeyama.linerchecker.ui.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yaeyama.linerchecker.R
import com.yaeyama.linerchecker.domain.repository.WeatherRepository
import com.yaeyama.linerchecker.ui.common.toErrorMessageRes
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import timber.log.Timber

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
                .map<_, WeatherUiState> { weatherInfo -> WeatherUiState.Success(weatherInfo) }
                .onStart { emit(WeatherUiState.Loading) }
                .catch { e ->
                    Timber.e(e, "fetchWeather failed")
                    emit(
                        WeatherUiState.Error(
                            e.toErrorMessageRes(
                                notFoundRes = R.string.weather_not_found,
                                fetchFailedRes = R.string.weather_fetch_failed,
                            ),
                        ),
                    )
                }
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
