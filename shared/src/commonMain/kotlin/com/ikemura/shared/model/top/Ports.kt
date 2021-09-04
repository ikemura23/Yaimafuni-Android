package com.ikemura.shared.model.top

import com.ikemura.shared.model.statusdetail.PortStatus
import kotlinx.serialization.Serializable

@Serializable
data class Ports(
    val anei: PortStatus = PortStatus(),
    val ykf: PortStatus = PortStatus(),
)
