package com.yaeyama.linerchecker.ui.weather

import app.cash.turbine.test
import com.yaeyama.linerchecker.domain.repository.WeatherRepository
import com.yaeyama.linerchecker.domain.weather.WeatherInfo
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `error from repository maps to WeatherUiState Error`() = runTest(testDispatcher) {
        val repository = mockk<WeatherRepository>()
        every { repository.fetchWeather() } returns flow { throw RuntimeException("boom") }
        val viewModel = WeatherViewModel(repository)

        viewModel.weatherFlow.test {
            assertEquals(WeatherUiState.Loading, awaitItem())
            assertEquals(WeatherUiState.Error, awaitItem())
        }
    }

    @Test
    fun `success from repository maps to WeatherUiState Success`() = runTest(testDispatcher) {
        val repository = mockk<WeatherRepository>()
        val weatherInfo = WeatherInfo()
        every { repository.fetchWeather() } returns flowOf(weatherInfo)
        val viewModel = WeatherViewModel(repository)

        viewModel.weatherFlow.test {
            assertEquals(WeatherUiState.Loading, awaitItem())
            assertEquals(WeatherUiState.Success(weatherInfo), awaitItem())
        }
    }

    @Test
    fun `retry re-invokes the repository and reflects the new result`() = runTest(testDispatcher) {
        val repository = mockk<WeatherRepository>()
        every { repository.fetchWeather() } returnsMany listOf(
            flow { throw RuntimeException("boom") },
            flowOf(WeatherInfo()),
        )
        val viewModel = WeatherViewModel(repository)

        viewModel.weatherFlow.test {
            assertEquals(WeatherUiState.Loading, awaitItem())
            assertEquals(WeatherUiState.Error, awaitItem())

            viewModel.retry()

            assertEquals(WeatherUiState.Loading, awaitItem())
            assertEquals(WeatherUiState.Success(WeatherInfo()), awaitItem())
        }
    }
}
