package com.yaeyama.linerchecker.domain.statusdetail

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class OperationStatusTest {

    @Test
    fun `normal and its typo nomal are NORMAL`() {
        assertEquals(OperationStatus.NORMAL, Status(code = "normal").toOperationStatus())
        assertEquals(OperationStatus.NORMAL, Status(code = "nomal").toOperationStatus())
    }

    @Test
    fun `cancel is CANCEL`() {
        assertEquals(OperationStatus.CANCEL, Status(code = "cancel").toOperationStatus())
    }

    @Test
    fun `cation and unknown codes are CAUTION`() {
        assertEquals(OperationStatus.CAUTION, Status(code = "cation").toOperationStatus())
        assertEquals(OperationStatus.CAUTION, Status(code = "xxx").toOperationStatus())
    }

    @Test
    fun `empty code has no operation status`() {
        assertNull(Status(code = "").toOperationStatus())
        assertFalse(Status(code = "").hasOperationStatus)
        assertTrue(Status(code = "normal").hasOperationStatus)
    }
}
