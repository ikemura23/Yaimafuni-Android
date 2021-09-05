package com.ikemura.shared.model.statusdetail

import kotlinx.serialization.Serializable

@Serializable
data class PortStatus(
    val comment: String = "",
    val portCode: String = "",
    val portName: String = "",
    val status: Status = Status(),
)
