package com.yaeyama_liner_checker.domain.top

import com.yaeyama_liner_checker.domain.statusdetail.PortStatus
import kotlinx.serialization.Serializable

@Serializable
data class Ports(
    val anei: PortStatus = PortStatus(),
    val ykf: PortStatus = PortStatus(),
)
