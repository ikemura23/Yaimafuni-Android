package com.yaeyama_liner_checker.domain.time_table

import kotlinx.serialization.Serializable

@Serializable
data class Header(
    val left: String = "",
    val right: String = ""
)
