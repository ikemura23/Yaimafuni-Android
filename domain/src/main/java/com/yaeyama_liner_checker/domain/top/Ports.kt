package com.yaeyama_liner_checker.domain.top

import com.yaeyama_liner_checker.domain.statusdetail.PortStatus

data class Ports(
    val anei: PortStatus = PortStatus(),
    val ykf: PortStatus = PortStatus(),
)
