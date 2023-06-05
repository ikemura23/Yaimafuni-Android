package data.repository

import com.ikemura.shared.repository.UiState
import com.ikemura.shared.repository.WeatherRepository
import com.yaeyama_liner_checker.domain.weather.Table
import com.yaeyama_liner_checker.domain.weather.Temperature
import com.yaeyama_liner_checker.domain.weather.Weather
import com.yaeyama_liner_checker.domain.weather.WeatherInfo
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent

class DebugWeatherRepositoryImpl : WeatherRepository, KoinComponent {
    override fun fetchWeather() = flow<UiState<WeatherInfo>> {
        val weatherInfo = WeatherInfo(
            today = Weather(
                date = "1月2日(土)",
                temperature = Temperature(
                    hight = "15℃",
                    low = "12℃",
                ),
                wave = "2.5メートル",
                weather = "曇り",
                wind = "北東の風やや強く",
                table = listOf(
                    Table(
                        hour = "06",
                        weather = "晴れ",
                        windBlow = "1",
                        windSpeed = "5",
                    ),
                ),
            ),
            tomorrow = Weather(
                date = "1月3日(日)",
                temperature = Temperature(
                    hight = "15℃",
                    low = "12℃",
                ),
                wave = "2.5メートル",
                weather = "曇り",
                wind = "北東の風やや強く",
                table = listOf(
                    Table(
                        hour = "06",
                        weather = "晴れ",
                        windBlow = "1",
                        windSpeed = "5",
                    ),
                ),
            ),
        )
        emit(UiState.Success(weatherInfo))
    }
}
