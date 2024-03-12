package com.ikmr.banbara23.yaeyama_liner_checker.ui.portstatusdetail

import com.yaeyama.linerchecker.ui.common.getStatusBackgroundColor
import com.yaeyama.linerchecker.ui.theme.StatusColor
import com.yaeyama_liner_checker.domain.statusdetail.Status
import org.junit.Assert.assertEquals
import org.junit.Test

class PortMainStatusKtTest {

    private val statusNomal = Status("nomal")
    private val statusCation = Status("cation")
    private val statusCancel = Status("cancel")

    @Test
    fun `ステータスが通常のとき、StatusColor Normal が返る`() {
        assertEquals(statusNomal.getStatusBackgroundColor(), StatusColor.Normal)
    }

    @Test
    fun `ステータスが未定のとき、StatusColor Cation が返る`() {
        assertEquals(statusCation.getStatusBackgroundColor(), StatusColor.Cation)
    }

    @Test
    fun `ステータスが欠航のとき、StatusColor Cancel が返る`() {
        assertEquals(statusCancel.getStatusBackgroundColor(), StatusColor.Cancel)
    }

    @Test
    fun `ステータスが不明（一致する値がない）とき、StatusColor Cation が返る`() {
        val statusXXX = Status("xxx")
        assertEquals(statusXXX.getStatusBackgroundColor(), StatusColor.Cation)
    }
}
