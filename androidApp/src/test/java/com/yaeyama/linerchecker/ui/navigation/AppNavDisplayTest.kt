package com.yaeyama.linerchecker.ui.navigation

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.junit4.StateRestorationTester
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import com.yaeyama.linerchecker.di.useCaseModule
import com.yaeyama.linerchecker.di.viewModelModule
import com.yaeyama.linerchecker.domain.repository.TopStatusRepository
import com.yaeyama.linerchecker.domain.repository.TyphoonRepository
import com.yaeyama.linerchecker.domain.top.TopPort
import com.yaeyama.linerchecker.domain.typhoon.Typhoon
import com.yaeyama.linerchecker.testing.ComponentActivityRegistrationRule
import com.yaeyama.linerchecker.testing.testRepositoryModule
import com.yaeyama.linerchecker.ui.dashboard.FakeDashBoardDataProvider
import com.yaeyama.linerchecker.ui.theme.YaimafuniAndroidTheme
import com.yaeyama.linerchecker.ui.typhoon.detail.TyphoonDetailUiModel
import io.mockk.every
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.compose.LocalKoinApplication
import org.koin.compose.LocalKoinScope
import org.koin.core.annotation.KoinInternalApi
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.get
import org.robolectric.RobolectricTestRunner

/**
 * Navigation 3 による画面遷移のテスト
 * 本番の useCaseModule / viewModelModule を、Repository だけテスト用に差し替えて使う
 */
@RunWith(RobolectricTestRunner::class)
class AppNavDisplayTest : KoinTest {

    @get:Rule(order = 0)
    val activityRegistrationRule = ComponentActivityRegistrationRule()

    @get:Rule(order = 1)
    val koinTestRule = KoinTestRule.create {
        modules(testRepositoryModule, useCaseModule, viewModelModule)
    }

    @get:Rule(order = 2)
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var backStack: NavBackStack<NavKey>

    @Before
    fun setUp() {
        every { get<TopStatusRepository>().fetchTopStatuses() } returns
            flowOf(TopPort(hatoma = FakeDashBoardDataProvider.dummyPort1))
        every { get<TyphoonRepository>().fetchTyphoonList() } returns
            flowOf(listOf(Typhoon(name = "台風1号")))
    }

    @Test
    fun `starts with the home screen`() {
        setContent()

        assertEquals(listOf(Main), backStack.toList())
        composeRule.onNodeWithText("鳩間島航路").assertIsDisplayed()
    }

    @Test
    fun `clicking a port opens its status detail with the port arguments`() {
        setContent()

        composeRule.onNodeWithText("鳩間島航路").performClick()

        assertEquals(
            listOf(Main, PortStatusDetail(portCode = "hatoma", portName = "鳩間島航路")),
            backStack.toList(),
        )
    }

    @Test
    fun `clicking a typhoon opens its detail with the typhoon data`() {
        setContent()

        composeRule.onNodeWithText("台風").performClick()
        composeRule.onNodeWithText("台風1号").performClick()

        assertEquals(TyphoonDetail(TyphoonDetailUiModel(name = "台風1号")), backStack.last())
        composeRule.onNodeWithText("台風1号").assertIsDisplayed()
    }

    @Test
    fun `system back on a detail screen returns to the home screen`() {
        setContent()
        composeRule.onNodeWithText("鳩間島航路").performClick()
        // クリックによる再コンポーズが反映されてから「戻る」を送る（反映前は NavDisplay の戻る処理がまだ無効のため）
        composeRule.waitForIdle()

        composeRule.runOnUiThread { composeRule.activity.onBackPressedDispatcher.onBackPressed() }
        composeRule.waitForIdle()

        assertEquals(listOf(Main), backStack.toList())
        composeRule.onNodeWithText("鳩間島航路").assertIsDisplayed()
    }

    @Test
    fun `top bar back button on a detail screen returns to the home screen`() {
        setContent()
        composeRule.onNodeWithText("台風").performClick()
        composeRule.onNodeWithText("台風1号").performClick()

        composeRule.onNodeWithContentDescription("戻る").performClick()

        assertEquals(listOf(Main), backStack.toList())
    }

    @Test
    fun `back stack is restored after the activity is recreated`() {
        val restorationTester = StateRestorationTester(composeRule)
        restorationTester.setContent { AppNavDisplayForTest(rememberNavBackStack(Main)) }
        composeRule.onNodeWithText("台風").performClick()
        composeRule.onNodeWithText("台風1号").performClick()

        restorationTester.emulateSavedInstanceStateRestore()

        assertEquals(
            listOf(Main, TyphoonDetail(TyphoonDetailUiModel(name = "台風1号"))),
            backStack.toList(),
        )
    }

    private fun setContent() {
        composeRule.setContent {
            AppNavDisplayForTest(remember { NavBackStack(Main) })
        }
    }

    /**
     * koin-androidx-compose 3.4 は最初に参照した Koin の root scope を CompositionLocal の既定値として保持し続けるため、
     * テストごとに作り直される Koin を明示的に渡す
     */
    @OptIn(KoinInternalApi::class)
    @Composable
    private fun AppNavDisplayForTest(backStack: NavBackStack<NavKey>) {
        this.backStack = backStack
        CompositionLocalProvider(
            LocalKoinApplication provides getKoin(),
            LocalKoinScope provides getKoin().scopeRegistry.rootScope,
        ) {
            YaimafuniAndroidTheme {
                AppNavDisplay(backStack = backStack)
            }
        }
    }
}
