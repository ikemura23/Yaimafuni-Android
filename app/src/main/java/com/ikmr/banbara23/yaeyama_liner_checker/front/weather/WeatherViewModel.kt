package com.ikmr.banbara23.yaeyama_liner_checker.front.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.ikmr.banbara23.yaeyama_liner_checker.WeatherRepository
import com.ikmr.banbara23.yaeyama_liner_checker.core.LiveEvent
import com.ikmr.banbara23.yaeyama_liner_checker.core.SingleLiveEvent
import com.ikmr.banbara23.yaeyama_liner_checker.model.weather.WeatherInfo
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

    val item = MutableLiveData<WeatherInfo>()
    var event = SingleLiveEvent<Nav>()

    @ExperimentalCoroutinesApi
    fun fetchWeather() {
        viewModelScope.launch {
            weatherRepository.fetchWeather().collect { state ->
                when (state) {
                    is WeatherUiState.Success -> {
                        item.value = state.weatherInfo
                    }
                    is WeatherUiState.Error -> {
                        event.postValue(Nav.Error)
                    }
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
