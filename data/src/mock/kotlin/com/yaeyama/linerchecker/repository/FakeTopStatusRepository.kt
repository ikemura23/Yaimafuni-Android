package com.yaeyama.linerchecker.repository

import com.yaeyama.linerchecker.domain.repository.TopStatusRepository
import com.yaeyama.linerchecker.domain.statusdetail.PortStatus
import com.yaeyama.linerchecker.domain.statusdetail.Status
import com.yaeyama.linerchecker.domain.top.Ports
import com.yaeyama.linerchecker.domain.top.TopPort
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeTopStatusRepository : TopStatusRepository {

    override fun fetchTopStatuses(): Flow<TopPort> = flow {
        delay(1000)
        val dummy = listOf(
            Ports(
                anei = PortStatus(
                    portCode = "taketomi",
                    portName = "ダミー竹富航路",
                    comment = "",
                    status = Status(
                        text = "通常運航",
                        code = "normal",
                    ),
                ),
                ykf = PortStatus(
                    portCode = "taketomi",
                    portName = "ダミー竹富航路",
                    comment = "",
                    status = Status(
                        text = "未定",
                        code = "cation",
                    ),
                ),
            ),
            Ports(
                anei = PortStatus(
                    portCode = "kohama",
                    portName = "ダミー小浜航路",
                    comment = "",
                    status = Status(
                        text = "通常運航",
                        code = "normal",
                    ),
                ),
                ykf = PortStatus(
                    portCode = "kohama",
                    portName = "ダミー小浜航路",
                    comment = "",
                    status = Status(
                        text = "通常運航",
                        code = "normal",
                    ),
                ),
            ),
            Ports(
                anei = PortStatus(
                    portCode = "kuroshima",
                    portName = "ダミー黒島航路",
                    comment = "",
                    status = Status(
                        text = "通常運航",
                        code = "normal",
                    ),
                ),
                ykf = PortStatus(
                    portCode = "kuroshima",
                    portName = "ダミー黒島航路",
                    comment = "",
                    status = Status(
                        text = "通常運航",
                        code = "normal",
                    ),
                ),
            ),
            Ports(
                anei = PortStatus(
                    portCode = "oohara",
                    portName = "ダミー大原航路",
                    comment = "",
                    status = Status(
                        text = "通常運航",
                        code = "normal",
                    ),
                ),
                ykf = PortStatus(
                    portCode = "oohara",
                    portName = "ダミー大原航路",
                    comment = "",
                    status = Status(
                        text = "通常運航",
                        code = "normal",
                    ),
                ),
            ),
            Ports(
                anei = PortStatus(
                    portCode = "uehara",
                    portName = "ダミー上原航路",
                    comment = "",
                    status = Status(
                        text = "通常運航",
                        code = "normal",
                    ),
                ),
                ykf = PortStatus(
                    portCode = "uehara",
                    portName = "ダミー上原航路",
                    comment = "",
                    status = Status(
                        text = "通常運航",
                        code = "normal",
                    ),
                ),
            ),
            Ports(
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
            ),
            Ports(
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
            ),
        )
        emit(
            TopPort(
                taketomi = dummy[0],
                kohama = dummy[1],
                kuroshima = dummy[2],
                oohara = dummy[3],
                uehara = dummy[4],
                hatoma = dummy[5],
                hateruma = dummy[6],
            ),
        )
    }
}
