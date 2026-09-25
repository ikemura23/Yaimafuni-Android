package com.yaeyama.linerchecker.ui.weather

import androidx.lifecycle.ViewModel
import com.yaeyama.linerchecker.R
import com.yaeyama.linerchecker.domain.usecase.GetWeatherInfo
import com.yaeyama.linerchecker.domain.weather.WeatherInfo
import com.yaeyama.linerchecker.ui.common.LoadState
import com.yaeyama.linerchecker.ui.common.asLoadState
import com.yaeyama.linerchecker.ui.common.reloadOnEach
import com.yaeyama.linerchecker.ui.common.stateInWhileSubscribed
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart

/**
 * 天気詳細 ViewModel
 */
class WeatherViewModel(
    private val getWeatherInfo: GetWeatherInfo,
) : ViewModel() {

    private val retryTrigger = MutableSharedFlow<Unit>(extraBufferCapacity = 1)

    val uiState: StateFlow<LoadState<WeatherInfo>> = retryTrigger
        .onStart { emit(Unit) }
        .reloadOnEach {
            getWeatherInfo().asLoadState(
                notFoundRes = R.string.weather_not_found,
                fetchFailedRes = R.string.weather_fetch_failed,
            )
        }
        .stateInWhileSubscribed(this, initialValue = LoadState.Loading)

    fun retry() {
        retryTrigger.tryEmit(Unit)
    }
}
