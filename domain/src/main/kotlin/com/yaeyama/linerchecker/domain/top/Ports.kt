package com.yaeyama.linerchecker.domain.top

import com.yaeyama.linerchecker.domain.statusdetail.PortStatus

data class Ports(
    val anei: PortStatus = PortStatus(),
    val ykf: PortStatus = PortStatus(),
)
