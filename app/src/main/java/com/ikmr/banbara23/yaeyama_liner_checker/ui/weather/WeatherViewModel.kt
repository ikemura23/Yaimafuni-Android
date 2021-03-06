package com.ikmr.banbara23.yaeyama_liner_checker.ui.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.ikmr.banbara23.yaeyama_liner_checker.core.Event
import com.ikmr.banbara23.yaeyama_liner_checker.core.toEvent
import com.ikemura.shared.model.weather.WeatherInfo
import com.ikemura.shared.repository.WeatherRepository
import com.ikemura.shared.repository.WeatherUiState
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * 天気詳細 ViewModel
 */
class WeatherScreenViewModel : ViewModel() {

    private val weatherRepository = WeatherRepository()

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
