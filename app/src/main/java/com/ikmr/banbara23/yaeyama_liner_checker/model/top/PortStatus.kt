package com.ikmr.banbara23.yaeyama_liner_checker.model.top

import com.ikemura.shared.model.statusdetail.Status

data class PortStatus(
    var comment: String = "",
    var portCode: String = "",
    var portName: String = "",
    var status: Status = Status(),
)
