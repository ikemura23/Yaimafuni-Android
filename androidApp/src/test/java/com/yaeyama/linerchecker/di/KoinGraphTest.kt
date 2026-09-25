package com.yaeyama.linerchecker.di

import com.yaeyama.linerchecker.domain.repository.TopStatusRepository
import com.yaeyama.linerchecker.domain.top.TopPort
import com.yaeyama.linerchecker.testing.testRepositoryModule
import com.yaeyama.linerchecker.ui.common.LoadState
import com.yaeyama.linerchecker.ui.dashboard.DashBoardViewModel
import com.yaeyama.linerchecker.ui.main.MainViewModel
import com.yaeyama.linerchecker.ui.portstatusdetail.PortStatusDetailViewModel
import com.yaeyama.linerchecker.ui.typhoon.list.TyphoonListViewModel
import com.yaeyama.linerchecker.ui.weather.WeatherViewModel
import io.mockk.every
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.get

/**
 * 本番の useCaseModule / viewModelModule を、Repository だけテスト用に差し替えて組み立てるテスト
 */
@OptIn(ExperimentalCoroutinesApi::class)
class KoinGraphTest : KoinTest {

    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(testRepositoryModule, useCaseModule, viewModelModule)
    }

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `every view model can be created from the graph`() {
        get<MainViewModel>()
        get<DashBoardViewModel>()
        get<WeatherViewModel>()
        get<TyphoonListViewModel>()
        get<PortStatusDetailViewModel>()
    }

    @Test
    fun `view model uses the repository provided by the test module`() = runTest(testDispatcher) {
        every { get<TopStatusRepository>().fetchTopStatuses() } returns flowOf(TopPort())
        val viewModel = get<DashBoardViewModel>()

        val collectorJob = launch { viewModel.uiState.collect {} }
        advanceUntilIdle()

        assertTrue(viewModel.uiState.value is LoadState.Success)
        collectorJob.cancel()
    }
}
