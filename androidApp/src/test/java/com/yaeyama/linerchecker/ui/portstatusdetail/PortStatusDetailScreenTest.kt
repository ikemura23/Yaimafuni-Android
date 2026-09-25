package com.yaeyama.linerchecker.ui.portstatusdetail

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.yaeyama.linerchecker.R
import com.yaeyama.linerchecker.domain.statusdetail.Status
import com.yaeyama.linerchecker.domain.timetable.Header
import com.yaeyama.linerchecker.domain.timetable.TimeTable
import com.yaeyama.linerchecker.testing.ComponentActivityRegistrationRule
import com.yaeyama.linerchecker.ui.theme.YaimafuniAndroidTheme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class PortStatusDetailScreenTest {

    @get:Rule(order = 0)
    val activityRegistrationRule = ComponentActivityRegistrationRule()

    @get:Rule(order = 1)
    val composeRule = createComposeRule()

    @Test
    fun `status and time table are displayed`() {
        composeRule.setContent {
            YaimafuniAndroidTheme {
                PortStatusDetailScreen(
                    portName = "竹富航路",
                    status = Status(code = "normal", text = "通常運航"),
                    statusDescription = "",
                    timeTable = TimeTable(header = Header(left = "石垣島", right = "竹富島")),
                )
            }
        }

        composeRule.onNodeWithText("竹富航路").assertIsDisplayed()
        composeRule.onNodeWithText("通常運航").assertIsDisplayed()
        composeRule.onNodeWithText("竹富島").assertIsDisplayed()
    }

    @Test
    fun `error state shows message and retry invokes callback`() {
        var retryCount = 0
        composeRule.setContent {
            YaimafuniAndroidTheme {
                PortStatusDetailScreen(
                    errorMessageRes = R.string.port_status_not_found,
                    portName = "",
                    status = Status(),
                    statusDescription = "",
                    timeTable = TimeTable(),
                    onRetry = { retryCount++ },
                )
            }
        }

        composeRule.onNodeWithText("この航路の運航情報は見つかりませんでした").assertIsDisplayed()
        composeRule.onNodeWithText("再試行").performClick()

        assertEquals(1, retryCount)
    }

    @Test
    fun `time table error is shown together with the status`() {
        composeRule.setContent {
            YaimafuniAndroidTheme {
                PortStatusDetailScreen(
                    timeTableErrorMessageRes = R.string.time_table_fetch_failed,
                    portName = "竹富航路",
                    status = Status(code = "normal", text = "通常運航"),
                    statusDescription = "",
                    timeTable = TimeTable(),
                )
            }
        }

        composeRule.onNodeWithText("竹富航路").assertIsDisplayed()
        composeRule.onNodeWithText("時刻表の取得に失敗しました").assertIsDisplayed()
    }
}
