package com.yaeyama.linerchecker.ui.dashboard

import app.cash.turbine.test
import com.yaeyama.linerchecker.R
import com.yaeyama.linerchecker.domain.repository.TopStatusRepository
import com.yaeyama.linerchecker.domain.top.TopPort
import com.yaeyama.linerchecker.domain.usecase.GetTopStatuses
import com.yaeyama.linerchecker.ui.common.LoadState
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DashBoardViewModelTest {

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
    fun `repository failure surfaces as Error instead of being silently dropped`() = runTest(testDispatcher) {
        val repository = mockk<TopStatusRepository>()
        every { repository.fetchTopStatuses() } returns flow { throw RuntimeException("boom") }
        val viewModel = DashBoardViewModel(GetTopStatuses(repository))

        val collectorJob = launch { viewModel.uiState.collect {} }
        advanceUntilIdle()

        assertEquals(LoadState.Error(R.string.dashboard_fetch_failed), viewModel.uiState.value)

        collectorJob.cancel()
    }

    @Test
    fun `port list is not resubscribed when the screen collects again soon`() = runTest(testDispatcher) {
        val repository = mockk<TopStatusRepository>()
        every { repository.fetchTopStatuses() } returns flowOf(TopPort())
        val viewModel = DashBoardViewModel(GetTopStatuses(repository))

        // タブの切り替えで画面が何度 collect しても再購読しない
        repeat(2) {
            val collectorJob = launch { viewModel.uiState.collect {} }
            advanceUntilIdle()
            collectorJob.cancel()
        }

        assertEquals(7, (viewModel.uiState.value as LoadState.Success).data.size)
        verify(exactly = 1) { repository.fetchTopStatuses() }
    }

    @Test
    fun `retry re-invokes the repository and reflects the new result`() = runTest(testDispatcher) {
        val repository = mockk<TopStatusRepository>()
        every { repository.fetchTopStatuses() } returnsMany listOf(
            flow { throw RuntimeException("boom") },
            flowOf(TopPort()),
        )
        val viewModel = DashBoardViewModel(GetTopStatuses(repository))

        viewModel.uiState.test {
            assertEquals(LoadState.Loading, awaitItem())
            assertEquals(LoadState.Error(R.string.dashboard_fetch_failed), awaitItem())

            viewModel.retry()

            assertEquals(LoadState.Loading, awaitItem())
            assertEquals(7, (awaitItem() as LoadState.Success).data.size)
        }
    }
}
