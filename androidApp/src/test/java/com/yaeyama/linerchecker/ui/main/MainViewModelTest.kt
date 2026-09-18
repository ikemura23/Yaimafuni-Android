package com.yaeyama.linerchecker.ui.main

import com.yaeyama_liner_checker.domain.repository.TyphoonRepository
import com.yaeyama_liner_checker.domain.typhoon.Typhoon
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
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
class MainViewModelTest {

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
    fun `typhoonCount is shared across multiple collectors instead of re-subscribing`() = runTest(testDispatcher) {
        val repository = mockk<TyphoonRepository>()
        val typhoonListFlow = MutableSharedFlow<List<Typhoon>>(replay = 1)
        every { repository.fetchTyphoonList() } returns typhoonListFlow
        val viewModel = MainViewModel(repository)

        typhoonListFlow.emit(listOf(Typhoon(), Typhoon()))

        // recompositionを模して複数回collectする
        val firstJob = launch { viewModel.typhoonCount.collect {} }
        advanceUntilIdle()
        val secondJob = launch { viewModel.typhoonCount.collect {} }
        advanceUntilIdle()

        assertEquals(2, viewModel.typhoonCount.value)
        verify(exactly = 1) { repository.fetchTyphoonList() }

        firstJob.cancel()
        secondJob.cancel()
    }
}
