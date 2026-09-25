package com.yaeyama.linerchecker.domain.top

/**
 * トップ画面に表示する全航路の運航状況（Firebase の `top_port`）
 *
 * Firebase からデシリアライズするため、プロパティ名は Firebase のキーと一致させること。
 * 表示順は [com.yaeyama.linerchecker.domain.usecase.GetTopStatuses] で決める。
 */
data class TopPort(
    /** 波照間航路 */
    var hateruma: Ports = Ports(),
    /** 鳩間航路 */
    var hatoma: Ports = Ports(),
    /** 小浜航路 */
    var kohama: Ports = Ports(),
    /** 竹富航路 */
    var taketomi: Ports = Ports(),
    /** 黒島航路 */
    var kuroshima: Ports = Ports(),
    /** 大原航路 */
    var oohara: Ports = Ports(),
    /** 上原航路 */
    var uehara: Ports = Ports(),
)
