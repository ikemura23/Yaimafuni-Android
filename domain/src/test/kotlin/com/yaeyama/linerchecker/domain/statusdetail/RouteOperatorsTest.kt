package com.yaeyama.linerchecker.domain.statusdetail

import org.junit.Assert.assertEquals
import org.junit.Test

class RouteOperatorsTest {

    @Test
    fun `hateruma is operated only by ANEI`() {
        assertEquals(listOf(Company.ANEI), RouteOperators.companiesOf("hateruma"))
    }

    @Test
    fun `other routes are operated by ANEI and YKF`() {
        assertEquals(listOf(Company.ANEI, Company.YKF), RouteOperators.companiesOf("taketomi"))
    }
}
