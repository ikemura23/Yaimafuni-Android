package com.ikmr.banbara23.yaeyama_liner_checker.ui.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ikemura.shared.repository.WeatherRepository
import com.ikemura.shared.repository.WeatherUiState
import com.ikmr.banbara23.yaeyama_liner_checker.core.Event
import com.ikmr.banbara23.yaeyama_liner_checker.core.toEvent
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * 天気詳細 ViewModel
 */
class WeatherScreenViewModel : ViewModel(), KoinComponent {

    private val weatherRepository: WeatherRepository by inject()

    // UI状態
    private val _state = MutableLiveData<WeatherUiState>()
    val state: LiveData<WeatherUiState> = _state

    // イベント
    val event = MutableLiveData<Event<Nav>>()
    fun getWeather() = weatherRepository.fetchWeather()

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
