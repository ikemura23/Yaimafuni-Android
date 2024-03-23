package com.banbara.yaeyama.liner.checker

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.ikemura23.yaeyama.linerchecker.ui.compose.dashboard.DashboardPage
import com.ikemura23.yaeyama.linerchecker.ui.theme.YaimafuniAndroidTheme
import org.junit.Rule
import org.junit.Test

class DashboardPageTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun myTest() {
        // Start the app
        composeTestRule.setContent {
            YaimafuniAndroidTheme {
                DashboardPage(
                    onRowClick = {}
                )
            }
        }

        composeTestRule.onNodeWithText("Hello やいまふに!").assertIsDisplayed()

        composeTestRule.onNodeWithText("Hello やいまふに!").performClick()
    }
}