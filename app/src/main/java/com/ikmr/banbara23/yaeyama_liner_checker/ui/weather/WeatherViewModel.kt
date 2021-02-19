package com.ikmr.banbara23.yaeyama_liner_checker.ui.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.ikmr.banbara23.yaeyama_liner_checker.core.LiveEvent
import com.ikmr.banbara23.yaeyama_liner_checker.core.SingleLiveEvent
import com.ikmr.banbara23.yaeyama_liner_checker.model.weather.WeatherInfo
import com.ikmr.banbara23.yaeyama_liner_checker.repository.WeatherRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
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

    private val _weather = MutableLiveData<WeatherInfo>()
    val weather: LiveData<WeatherInfo> = _weather
    var event = SingleLiveEvent<Nav>()

    @ExperimentalCoroutinesApi
    fun fetchWeather() {
        viewModelScope.launch {
            // StateFlowの購読
            weatherRepository.fetchWeather().collect { state ->
                when (state) {
                    // 成功
                    is WeatherUiState.Success -> _weather.value = state.weatherInfo
                    // エラー処理
                    is WeatherUiState.Error -> event.postValue(Nav.Error)
                }
            }
        }
    }

    /**
     * 天気を詳しく見るをクリック
     */
    fun moreButtonClick() {
        event.setValue(Nav.More)
    }

    sealed class Nav : LiveEvent {
        object Error : Nav()
        object More : Nav()
    }
}

sealed class WeatherUiState {
    data class Success(val weatherInfo: WeatherInfo) : WeatherUiState()
    data class Error(val message: String) : WeatherUiState()
}
