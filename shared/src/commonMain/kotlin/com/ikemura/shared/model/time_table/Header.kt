package com.ikemura.shared.model.time_table

import kotlinx.serialization.Serializable

@Serializable
data class Header(
    val left: String = "",
    val right: String = ""
)
