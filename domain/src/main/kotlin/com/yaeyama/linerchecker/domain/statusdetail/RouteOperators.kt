package com.yaeyama.linerchecker.domain.statusdetail

/**
 * 航路ごとの運航会社を定義するドメインサービス
 */
object RouteOperators {

    private const val HATERUMA = "hateruma"

    /**
     * [portCode] の航路を運航している会社を表示順で返す
     * 波照間航路は安栄観光のみが運航している
     */
    fun companiesOf(portCode: String): List<Company> = when (portCode) {
        HATERUMA -> listOf(Company.ANEI)
        else -> listOf(Company.ANEI, Company.YKF)
    }
}
