package com.ikmr.banbara23.yaeyama_liner_checker.model.top

data class TopPort(
    var hateruma: Ports = Ports(),
    var hatoma: Ports = Ports(),
    var kohama: Ports = Ports(),
    var taketomi: Ports = Ports(),
    var kuroshima: Ports = Ports(),
    var oohara: Ports = Ports(),
    var uehara: Ports = Ports(),
)

data class Ports(
    val anei: PortStatus = PortStatus(),
    val ykf: PortStatus = PortStatus(),
)

data class PortStatus(
    var comment: String = "",
    var portCode: String = "",
    var portName: String = "",
    var status: Status = Status(),
)
