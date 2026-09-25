package com.yaeyama.linerchecker.ui.portstatusdetail

import com.yaeyama.linerchecker.R
import com.yaeyama_liner_checker.domain.common.DataNotFoundException
import com.yaeyama_liner_checker.domain.repository.StatusDetailRepository
import com.yaeyama_liner_checker.domain.statusdetail.Company
import com.yaeyama_liner_checker.domain.statusdetail.PortStatus
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
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
class PortStatusDetailViewModelTest {

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
    fun `isLoading becomes false once the status detail arrives`() = runTest(testDispatcher) {
        val repository = mockk<StatusDetailRepository>()
        val statusDetailFlow = MutableSharedFlow<PortStatus>(replay = 1)
        every { repository.fetchStatusDetail(Company.ANEI, "ishigaki") } returns statusDetailFlow
        every { repository.fetchTimeTable(Company.ANEI, "ishigaki") } returns MutableSharedFlow(replay = 1)
        val viewModel = PortStatusDetailViewModel(repository)

        val collectorJob = launch { viewModel.uiState.collect {} }
        viewModel.fetchDetail(Company.ANEI, "ishigaki")
        advanceUntilIdle()
        assertTrue(viewModel.uiState.value.isLoading)

        statusDetailFlow.emit(PortStatus(portName = "石垣島"))
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertEquals("石垣島", state.portStatus.portName)

        collectorJob.cancel()
    }

    @Test
    fun `status detail failure surfaces as isError`() = runTest(testDispatcher) {
        val repository = mockk<StatusDetailRepository>()
        every { repository.fetchStatusDetail(Company.ANEI, "ishigaki") } returns flow { throw RuntimeException("boom") }
        every { repository.fetchTimeTable(Company.ANEI, "ishigaki") } returns MutableSharedFlow(replay = 1)
        val viewModel = PortStatusDetailViewModel(repository)

        val collectorJob = launch { viewModel.uiState.collect {} }
        viewModel.fetchDetail(Company.ANEI, "ishigaki")
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertTrue(state.isError)

        collectorJob.cancel()
    }

    @Test
    fun `time table failure surfaces as isTimeTableError without affecting isError`() = runTest(testDispatcher) {
        val repository = mockk<StatusDetailRepository>()
        every { repository.fetchStatusDetail(Company.ANEI, "ishigaki") } returns MutableSharedFlow(replay = 1)
        every { repository.fetchTimeTable(Company.ANEI, "ishigaki") } returns flow { throw RuntimeException("boom") }
        val viewModel = PortStatusDetailViewModel(repository)

        val collectorJob = launch { viewModel.uiState.collect {} }
        viewModel.fetchDetail(Company.ANEI, "ishigaki")
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state.isTimeTableError)
        assertFalse(state.isError)

        collectorJob.cancel()
    }

    @Test
    fun `not found errors surface as not found messages`() = runTest(testDispatcher) {
        val repository = mockk<StatusDetailRepository>()
        every { repository.fetchStatusDetail(Company.YKF, "hateruma") } returns flow { throw DataNotFoundException("ykf/hateruma") }
        every { repository.fetchTimeTable(Company.YKF, "hateruma") } returns flow { throw DataNotFoundException("ykf_timeTable/hateruma") }
        val viewModel = PortStatusDetailViewModel(repository)

        val collectorJob = launch { viewModel.uiState.collect {} }
        viewModel.fetchDetail(Company.YKF, "hateruma")
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertEquals(R.string.port_status_not_found, state.errorMessageRes)
        assertEquals(R.string.time_table_not_found, state.timeTableErrorMessageRes)

        collectorJob.cancel()
    }

    @Test
    fun `other errors surface as fetch failed messages`() = runTest(testDispatcher) {
        val repository = mockk<StatusDetailRepository>()
        every { repository.fetchStatusDetail(Company.ANEI, "ishigaki") } returns flow { throw RuntimeException("boom") }
        every { repository.fetchTimeTable(Company.ANEI, "ishigaki") } returns flow { throw RuntimeException("boom") }
        val viewModel = PortStatusDetailViewModel(repository)

        val collectorJob = launch { viewModel.uiState.collect {} }
        viewModel.fetchDetail(Company.ANEI, "ishigaki")
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertEquals(R.string.port_status_fetch_failed, state.errorMessageRes)
        assertEquals(R.string.time_table_fetch_failed, state.timeTableErrorMessageRes)

        collectorJob.cancel()
    }
}
