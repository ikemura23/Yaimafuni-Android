package com.ikmr.banbara23.yaeyama_liner_checker.ui.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.ikmr.banbara23.yaeyama_liner_checker.core.Event
import com.ikmr.banbara23.yaeyama_liner_checker.core.toEvent
import com.ikmr.banbara23.yaeyama_liner_checker.model.weather.WeatherInfo
import com.ikmr.banbara23.yaeyama_liner_checker.repository.WeatherRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * 天気詳細 ViewModel
 */
class WeatherScreenViewModel : ViewModel() {

    private val database: DatabaseReference by lazy {
        FirebaseDatabase.getInstance().reference.ref.child("weather")
    }
    private val weatherRepository: WeatherRepository by lazy {
        WeatherRepository(database)
    }

    // UI状態
    private val _state = MutableLiveData<WeatherUiState>()
    val state: LiveData<WeatherUiState> = _state

    // イベント
    val event = MutableLiveData<Event<Nav>>()

    fun fetchWeather() {
        viewModelScope.launch {
            // StateFlowの購読
            weatherRepository.fetchWeather().collect { state ->
                when (state) {
                    // 成功
                    is WeatherUiState.Success -> _state.value = WeatherUiState.Success(state.weatherInfo)
                    // エラー処理
                    is WeatherUiState.Error -> _state.value = WeatherUiState.Error(state.message)
                }
            }
        }
    }

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

sealed class WeatherUiState {
    data class Success(val weatherInfo: WeatherInfo) : WeatherUiState()
    data class Error(val message: String) : WeatherUiState()
}
