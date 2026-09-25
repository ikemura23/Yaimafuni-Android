package com.yaeyama.linerchecker.ui.portstatusdetail

import com.yaeyama.linerchecker.domain.statusdetail.Status
import com.yaeyama.linerchecker.ui.common.getStatusBackgroundColor
import com.yaeyama.linerchecker.ui.theme.StatusColor
import org.junit.Assert.assertEquals
import org.junit.Test

class PortMainStatusKtTest {

    private val statusNormal = Status("nomal")
    private val statusCaution = Status("cation")
    private val statusCancel = Status("cancel")

    @Test
    fun `ステータスが通常のとき、StatusColor Normal が返る`() {
        assertEquals(statusNormal.getStatusBackgroundColor(), StatusColor.Normal)
    }

    @Test
    fun `ステータスが未定のとき、StatusColor Caution が返る`() {
        assertEquals(statusCaution.getStatusBackgroundColor(), StatusColor.Caution)
    }

    @Test
    fun `ステータスが欠航のとき、StatusColor Cancel が返る`() {
        assertEquals(statusCancel.getStatusBackgroundColor(), StatusColor.Cancel)
    }

    @Test
    fun `ステータスが不明（一致する値がない）とき、StatusColor Caution が返る`() {
        val statusXXX = Status("xxx")
        assertEquals(statusXXX.getStatusBackgroundColor(), StatusColor.Caution)
    }
}
