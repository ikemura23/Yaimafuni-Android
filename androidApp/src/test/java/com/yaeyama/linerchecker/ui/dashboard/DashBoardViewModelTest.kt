package com.yaeyama.linerchecker.ui.dashboard

import com.yaeyama_liner_checker.domain.repository.TopStatusRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
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
        val viewModel = DashBoardViewModel(repository)

        val collectorJob = launch { viewModel.uiState.collect {} }
        viewModel.fetchPortList()
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state.isError)
        assertFalse(state.isLoading)
        assertTrue(state.portList.isEmpty())

        collectorJob.cancel()
    }
}
