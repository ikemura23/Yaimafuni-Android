package com.yaeyama_liner_checker.domain.common

sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<out T>(val data: T) : UiState<T>()
    data class Error(val error: Throwable) : UiState<Nothing>()
}
