package com.yaeyama.linerchecker.ui.dashboard

import com.yaeyama.linerchecker.domain.statusdetail.PortStatus
import com.yaeyama.linerchecker.domain.statusdetail.Status
import com.yaeyama.linerchecker.domain.top.Ports

object FakeDashBoardDataProvider {

    val dummyPort1 = Ports(
        anei = PortStatus(
            portCode = "hatoma",
            portName = "鳩間島航路",
            comment = "海上時化の為、全便欠航。",
            status = Status(
                text = "通常運航",
                code = "normal",
            ),
        ),
        ykf = PortStatus(
            portCode = "hatoma",
            portName = "鳩間",
            comment = "海上時化の為、全便欠航。",
            status = Status(
                text = "欠航",
                code = "cancel",
            ),
        ),
    )

    val dummyPort2 = Ports(
        anei = PortStatus(
            portCode = "hatoma",
            portName = "上原航路",
            comment = "海上時化の為、全便欠航。",
            status = Status(
                text = "通常運航",
                code = "normal",
            ),
        ),
        ykf = PortStatus(
            portCode = "hatoma",
            portName = "上原",
            comment = "海上時化の為、全便欠航。",
            status = Status(
                text = "欠航",
                code = "cancel",
            ),
        ),
    )
    val dummyPortList: List<Ports> = (1..5).toList().map { dummyPort1 } + dummyPort2
}
