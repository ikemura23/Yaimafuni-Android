package com.yaeyama.linerchecker.domain.statusdetail

data class PortStatus(
    val comment: String = "",
    val portCode: String = "",
    val portName: String = "",
    val status: Status = Status(),
)
