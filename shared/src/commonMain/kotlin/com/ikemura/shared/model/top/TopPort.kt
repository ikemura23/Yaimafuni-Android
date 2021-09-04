package com.ikemura.shared.model.top

import kotlinx.serialization.Serializable

@Serializable
data class TopPort(
    var hateruma: Ports = Ports(),
    var hatoma: Ports = Ports(),
    var kohama: Ports = Ports(),
    var taketomi: Ports = Ports(),
    var kuroshima: Ports = Ports(),
    var oohara: Ports = Ports(),
    var uehara: Ports = Ports(),
)
