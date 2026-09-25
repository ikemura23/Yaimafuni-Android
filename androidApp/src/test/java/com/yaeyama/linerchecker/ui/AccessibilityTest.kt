package com.yaeyama.linerchecker.ui

import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import com.yaeyama.linerchecker.R
import com.yaeyama.linerchecker.testing.ComponentActivityRegistrationRule
import com.yaeyama.linerchecker.ui.common.LoadState
import com.yaeyama.linerchecker.ui.common.compose.ErrorContent
import com.yaeyama.linerchecker.ui.dashboard.DashBoardScreen
import com.yaeyama.linerchecker.ui.dashboard.FakeDashBoardDataProvider
import com.yaeyama.linerchecker.ui.main.compose.MainBottomNavigation
import com.yaeyama.linerchecker.ui.main.compose.MainTab
import com.yaeyama.linerchecker.ui.theme.YaimafuniAndroidTheme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class AccessibilityTest {

    @get:Rule(order = 0)
    val activityRegistrationRule = ComponentActivityRegistrationRule()

    @get:Rule(order = 1)
    val composeRule = createComposeRule()

    @Test
    fun `dashboard row is one clickable item with a click label and the full company name`() {
        composeRule.setContent {
            YaimafuniAndroidTheme {
                DashBoardScreen(uiState = LoadState.Success(listOf(FakeDashBoardDataProvider.dummyPort2)), onRowClick = {})
            }
        }

        composeRule.onNodeWithText("上原航路", useUnmergedTree = false)
            .assert(hasClickAction())
            .assert(
                SemanticsMatcher("has click label") {
                    it.config.getOrElseNullable(SemanticsActions.OnClick) { null }?.label == "運航詳細を表示"
                },
            )
        // 画面上は「八観フェ」だが、読み上げは正式名称
        composeRule.onNodeWithContentDescription("八重山観光フェリー", useUnmergedTree = true).assertExists()
    }

    @Test
    fun `error message is announced as a live region`() {
        composeRule.setContent {
            YaimafuniAndroidTheme {
                ErrorContent(messageRes = R.string.dashboard_fetch_failed, onRetry = {})
            }
        }

        val node = composeRule.onNodeWithText("運航情報の取得に失敗しました").fetchSemanticsNode()
        assertEquals(LiveRegionMode.Polite, node.config[SemanticsProperties.LiveRegion])
    }

    @Test
    fun `typhoon badge is read as a sentence`() {
        composeRule.setContent {
            YaimafuniAndroidTheme {
                MainBottomNavigation(selectedTab = MainTab.Dashboard, onTabSelected = {}, typhoonBadgeCount = 2)
            }
        }

        composeRule.onNodeWithContentDescription("発生中の台風 2件", useUnmergedTree = true).assertExists()
    }
}
