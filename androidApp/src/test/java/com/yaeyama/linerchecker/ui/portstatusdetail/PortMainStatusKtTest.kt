package com.yaeyama.linerchecker.ui.portstatusdetail

import com.yaeyama.linerchecker.domain.statusdetail.Status
import com.yaeyama.linerchecker.ui.common.getStatusBackgroundColor
import com.yaeyama.linerchecker.ui.theme.StatusColor
import org.junit.Assert.assertEquals
import org.junit.Test

class PortMainStatusKtTest {

    private val statusNomal = Status("nomal")
    private val statusCation = Status("cation")
    private val statusCancel = Status("cancel")

    @Test
    fun `ステータスが通常のとき、StatusColor Normal が返る`() {
        assertEquals(StatusColor.Normal, statusNomal.getStatusBackgroundColor())
    }

    @Test
    fun `ステータスが未定のとき、StatusColor Cation が返る`() {
        assertEquals(StatusColor.Cation, statusCation.getStatusBackgroundColor())
    }

    @Test
    fun `ステータスが欠航のとき、StatusColor Cancel が返る`() {
        assertEquals(StatusColor.Cancel, statusCancel.getStatusBackgroundColor())
    }

    @Test
    fun `ステータスが不明（一致する値がない）とき、StatusColor Cation が返る`() {
        val statusXXX = Status("xxx")
        assertEquals(StatusColor.Cation, statusXXX.getStatusBackgroundColor())
    }
}
