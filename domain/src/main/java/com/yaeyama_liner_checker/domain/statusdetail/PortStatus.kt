package com.yaeyama_liner_checker.domain.statusdetail

import kotlinx.serialization.Serializable

@Serializable
data class PortStatus(
    val comment: String = "",
    val portCode: String = "",
    val portName: String = "",
    val status: Status = Status(),
)
