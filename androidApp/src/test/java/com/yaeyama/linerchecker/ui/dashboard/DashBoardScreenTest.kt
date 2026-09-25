package com.yaeyama.linerchecker.ui.dashboard

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.yaeyama.linerchecker.R
import com.yaeyama.linerchecker.domain.top.Ports
import com.yaeyama.linerchecker.testing.ComponentActivityRegistrationRule
import com.yaeyama.linerchecker.ui.theme.YaimafuniAndroidTheme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class DashBoardScreenTest {

    @get:Rule(order = 0)
    val activityRegistrationRule = ComponentActivityRegistrationRule()

    @get:Rule(order = 1)
    val composeRule = createComposeRule()

    @Test
    fun `port list is displayed`() {
        val ports = listOf(FakeDashBoardDataProvider.dummyPort1, FakeDashBoardDataProvider.dummyPort2)
        setContent(DashBoardUiState(isLoading = false, errorMessageRes = null, portList = ports))

        composeRule.onNodeWithText("鳩間島航路").assertIsDisplayed()
        composeRule.onNodeWithText("上原航路").assertIsDisplayed()
    }

    @Test
    fun `clicking a row passes the port`() {
        var clickedPortName: String? = null
        setContent(
            DashBoardUiState(isLoading = false, errorMessageRes = null, portList = listOf(FakeDashBoardDataProvider.dummyPort2)),
            onRowClick = { clickedPortName = it.anei.portName },
        )

        composeRule.onNodeWithText("上原航路").performClick()

        assertEquals("上原航路", clickedPortName)
    }

    @Test
    fun `error state shows message and retry invokes callback`() {
        var retryCount = 0
        setContent(
            DashBoardUiState(isLoading = false, errorMessageRes = R.string.dashboard_fetch_failed, portList = emptyList()),
            onRetry = { retryCount++ },
        )

        composeRule.onNodeWithText("運航情報の取得に失敗しました").assertIsDisplayed()
        composeRule.onNodeWithText("通信環境の良い場所で、もう一度お試しください").assertIsDisplayed()
        composeRule.onNodeWithText("再試行").performClick()

        assertEquals(1, retryCount)
    }

    @Test
    fun `loading state does not show the port list or error`() {
        setContent(DashBoardUiState(isLoading = true, errorMessageRes = null, portList = FakeDashBoardDataProvider.dummyPortList))

        composeRule.onNodeWithText("上原航路").assertDoesNotExist()
        composeRule.onNodeWithText("再試行").assertDoesNotExist()
    }

    private fun setContent(
        uiState: DashBoardUiState,
        onRowClick: (Ports) -> Unit = {},
        onRetry: () -> Unit = {},
    ) {
        composeRule.setContent {
            YaimafuniAndroidTheme {
                DashBoardScreen(uiState = uiState, onRowClick = onRowClick, onRetry = onRetry)
            }
        }
    }
}
