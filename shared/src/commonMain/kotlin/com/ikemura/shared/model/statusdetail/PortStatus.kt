package com.ikemura.shared.model.statusdetail

data class PortStatus(
    val comment: String = "",
    val portCode: String = "",
    val portName: String = "",
    val status: Status = Status()
)
