package com.ikemura.shared.model.statusdetail

import kotlinx.serialization.Serializable

@Serializable
data class Status(
    val code: String = "",
    val text: String = ""
)
