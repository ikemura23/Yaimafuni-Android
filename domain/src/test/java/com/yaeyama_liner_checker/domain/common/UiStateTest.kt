package com.yaeyama_liner_checker.domain.common

import org.junit.Assert.assertTrue
import org.junit.Test

class UiStateTest {

    @Test
    fun `Loading is a UiState so it can be emitted alongside Success and Error`() {
        val states: List<UiState<Int>> = listOf(
            UiState.Loading,
            UiState.Success(1),
            UiState.Error(RuntimeException("boom")),
        )

        assertTrue(states[0] is UiState.Loading)
    }
}
