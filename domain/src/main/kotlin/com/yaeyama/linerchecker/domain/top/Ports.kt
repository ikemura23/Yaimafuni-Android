package com.yaeyama.linerchecker.domain.top

import com.yaeyama.linerchecker.domain.statusdetail.PortStatus

/**
 * 1航路の会社別の運航状況
 */
data class Ports(
    /** 安栄観光の運航状況 */
    val anei: PortStatus = PortStatus(),
    /** 八重山観光フェリーの運航状況 */
    val ykf: PortStatus = PortStatus(),
)
