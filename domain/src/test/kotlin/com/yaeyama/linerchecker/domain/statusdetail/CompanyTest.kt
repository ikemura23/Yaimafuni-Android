package com.yaeyama.linerchecker.domain.statusdetail

import org.junit.Assert.assertEquals
import org.junit.Test

class CompanyTest {

    @Test
    fun `toString includes code and full name`() {
        assertEquals(
            "Company{code='anei', name='安栄観光'}",
            Company.ANEI.toString(),
        )
    }

    @Test
    fun `code and fullName are exposed per entry`() {
        assertEquals("ykf", Company.YKF.code)
        assertEquals("八重山観光フェリー", Company.YKF.fullName)
    }
}
