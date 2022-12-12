package com.ikmr.banbara23.yaeyama_liner_checker.ui.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikemura.shared.repository.UiState
import com.ikemura.shared.repository.WeatherRepository
import com.ikmr.banbara23.yaeyama_liner_checker.core.Event
import com.ikmr.banbara23.yaeyama_liner_checker.core.toEvent
import com.yaeyama_liner_checker.domain.weather.WeatherInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filter
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

    // UI状態
    private val _state = MutableStateFlow(WeatherUiState.Loading)
    val state: StateFlow<WeatherUiState> = _state

    val weatherFlow: StateFlow<WeatherUiState> = weatherRepository.fetchWeather()
        // .filter { it is UiState.Success }
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
            initialValue = WeatherUiState.Loading
        )

    // イベント
    val event = MutableLiveData<Event<Nav>>()

    suspend fun getWeather(): StateFlow<UiState<WeatherInfo>> = weatherRepository.fetchWeather()
        .stateIn(scope = viewModelScope)

    /**
     * 天気を詳しく見るをクリック
     */
    fun moreButtonClick() {
        event.value = Nav.More.toEvent()
    }

    sealed class Nav {
        object Error : Nav()
        object More : Nav()
    }
}
