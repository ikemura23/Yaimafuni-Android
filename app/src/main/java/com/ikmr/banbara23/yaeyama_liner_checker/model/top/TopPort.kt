package com.ikmr.banbara23.yaeyama_liner_checker.model.top

import com.ikemura.shared.model.top.Ports

data class TopPort(
    var hateruma: Ports = Ports(),
    var hatoma: Ports = Ports(),
    var kohama: Ports = Ports(),
    var taketomi: Ports = Ports(),
    var kuroshima: Ports = Ports(),
    var oohara: Ports = Ports(),
    var uehara: Ports = Ports(),
)
