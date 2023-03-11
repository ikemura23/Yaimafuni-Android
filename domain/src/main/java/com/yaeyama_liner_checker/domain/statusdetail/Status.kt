package com.yaeyama_liner_checker.domain.statusdetail

import kotlinx.serialization.Serializable

@Serializable
data class Status(
    val code: String = "",
    val text: String = "",
)
