package com.yaeyama.linerchecker.ui.dashboard

import com.yaeyama.linerchecker.domain.repository.TopStatusRepository
import com.yaeyama.linerchecker.domain.top.TopPort
import com.yaeyama.linerchecker.domain.usecase.GetTopStatuses
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
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
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
    fun `repository failure surfaces as isError instead of being silently dropped`() = runTest(testDispatcher) {
        val repository = mockk<TopStatusRepository>()
        every { repository.fetchTopStatuses() } returns flow { throw RuntimeException("boom") }
        val viewModel = DashBoardViewModel(GetTopStatuses(repository))

        val collectorJob = launch { viewModel.uiState.collect {} }
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state.isError)
        assertFalse(state.isLoading)
        assertTrue(state.portList.isEmpty())

        collectorJob.cancel()
    }

    @Test
    fun `port list is subscribed once when the view model is created`() = runTest(testDispatcher) {
        val repository = mockk<TopStatusRepository>()
        every { repository.fetchTopStatuses() } returns flowOf(TopPort())
        val viewModel = DashBoardViewModel(GetTopStatuses(repository))

        // タブの切り替えで画面が何度 collect しても再購読しない
        repeat(2) {
            val collectorJob = launch { viewModel.uiState.collect {} }
            advanceUntilIdle()
            collectorJob.cancel()
        }

        assertEquals(7, viewModel.uiState.value.portList.size)
        verify(exactly = 1) { repository.fetchTopStatuses() }
    }
}
