package com.yaeyama.linerchecker.ui.weather

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.yaeyama.linerchecker.domain.weather.Weather
import com.yaeyama.linerchecker.domain.weather.WeatherInfo
import com.yaeyama.linerchecker.testing.ComponentActivityRegistrationRule
import com.yaeyama.linerchecker.ui.theme.YaimafuniAndroidTheme
import com.yaeyama.linerchecker.ui.weather.compose.WeatherPage
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class WeatherPageTest {

    @get:Rule(order = 0)
    val activityRegistrationRule = ComponentActivityRegistrationRule()

    @get:Rule(order = 1)
    val composeRule = createComposeRule()

    @Test
    fun `today and tomorrow weather are displayed`() {
        val weatherInfo = WeatherInfo(
            today = Weather(date = "1月2日(土)", weather = "晴れ"),
            tomorrow = Weather(date = "1月3日(日)", weather = "曇り"),
        )
        setContent(WeatherUiState.Success(weatherInfo))

        composeRule.onNodeWithText("1月2日(土)").assertIsDisplayed()
        composeRule.onNodeWithText("晴れ").assertIsDisplayed()
        composeRule.onNodeWithText("1月3日(日)").assertIsDisplayed()
        composeRule.onNodeWithText("曇り").assertIsDisplayed()
    }

    @Test
    fun `error state shows message and retry invokes callback`() {
        var retryCount = 0
        setContent(WeatherUiState.Error, onRetry = { retryCount++ })

        composeRule.onNodeWithText("天気データの取得に失敗しました").assertIsDisplayed()
        composeRule.onNodeWithText("再試行").performClick()

        assertEquals(1, retryCount)
    }

    @Test
    fun `loading state does not show error`() {
        setContent(WeatherUiState.Loading)

        composeRule.onNodeWithText("再試行").assertDoesNotExist()
    }

    private fun setContent(uiState: WeatherUiState, onRetry: () -> Unit = {}) {
        composeRule.setContent {
            YaimafuniAndroidTheme {
                WeatherPage(uiState = uiState, onRetry = onRetry)
            }
        }
    }
}
