package com.ikmr.banbara23.yaeyama_liner_checker.model

import com.ikmr.banbara23.yaeyama_liner_checker.model.top.PortStatus

data class Ports(
    val anei: PortStatus = PortStatus(),
    val ykf: PortStatus = PortStatus(),
)
