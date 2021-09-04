package com.ikemura.shared.model.top

import com.ikemura.shared.model.statusdetail.PortStatus

data class Ports(
    val anei: PortStatus = PortStatus(),
    val ykf: PortStatus = PortStatus(),
)
