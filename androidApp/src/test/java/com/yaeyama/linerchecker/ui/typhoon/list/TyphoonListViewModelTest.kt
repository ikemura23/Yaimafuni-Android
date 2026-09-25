package com.yaeyama.linerchecker.ui.typhoon.list

import app.cash.turbine.test
import com.yaeyama.linerchecker.R
import com.yaeyama.linerchecker.domain.repository.TyphoonRepository
import com.yaeyama.linerchecker.domain.typhoon.Typhoon
import com.yaeyama.linerchecker.domain.usecase.GetTyphoonList
import com.yaeyama.linerchecker.ui.common.LoadState
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
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
    fun `repository failure surfaces as LoadState Error instead of being silently dropped`() = runTest(testDispatcher) {
        val repository = mockk<TyphoonRepository>()
        every { repository.fetchTyphoonList() } returns flow { throw RuntimeException("boom") }
        val viewModel = TyphoonListViewModel(GetTyphoonList(repository))

        viewModel.uiState.test {
            assertEquals(LoadState.Loading, awaitItem())
            assertEquals(LoadState.Error(R.string.typhoon_list_fetch_failed), awaitItem())
        }
    }

    @Test
    fun `retry re-invokes the repository and reflects the new result`() = runTest(testDispatcher) {
        val repository = mockk<TyphoonRepository>()
        val typhoons = listOf(Typhoon(name = "台風1号"))
        every { repository.fetchTyphoonList() } returnsMany listOf(
            flow { throw RuntimeException("boom") },
            flowOf(typhoons),
        )
        val viewModel = TyphoonListViewModel(GetTyphoonList(repository))

        viewModel.uiState.test {
            assertEquals(LoadState.Loading, awaitItem())
            assertEquals(LoadState.Error(R.string.typhoon_list_fetch_failed), awaitItem())

            viewModel.retry()

            assertEquals(LoadState.Loading, awaitItem())
            assertEquals(LoadState.Success(typhoons), awaitItem())
        }
    }
}
