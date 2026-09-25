package com.yaeyama.linerchecker.ui.typhoon.list

import app.cash.turbine.test
import com.yaeyama.linerchecker.domain.repository.TyphoonRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TyphoonListViewModelTest {

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
    fun `repository failure surfaces as TyphoonUiState Error instead of being silently dropped`() = runTest(testDispatcher) {
        val repository = mockk<TyphoonRepository>()
        every { repository.fetchTyphoonList() } returns flow { throw RuntimeException("boom") }
        val viewModel = TyphoonListViewModel(repository)

        viewModel.uiState.test {
            assertEquals(TyphoonUiState.Loading, awaitItem())
            assertEquals(TyphoonUiState.Error, awaitItem())
        }
    }
}
