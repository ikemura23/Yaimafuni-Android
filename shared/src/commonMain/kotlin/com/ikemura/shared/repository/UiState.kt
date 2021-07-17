package com.ikemura.shared.repository

sealed class UiState<out T> {
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val error: Throwable) : UiState<Nothing>()
}
