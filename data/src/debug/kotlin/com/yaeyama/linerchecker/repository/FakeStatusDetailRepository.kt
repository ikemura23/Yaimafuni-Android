package com.yaeyama.linerchecker.repository

import android.util.Log
import com.yaeyama_liner_checker.domain.statusdetail.Company
import com.yaeyama_liner_checker.domain.statusdetail.PortStatus
import com.yaeyama_liner_checker.domain.statusdetail.Status
import com.yaeyama_liner_checker.domain.time_table.Header
import com.yaeyama_liner_checker.domain.time_table.Row
import com.yaeyama_liner_checker.domain.time_table.RowItem
import com.yaeyama_liner_checker.domain.time_table.TimeTable
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import org.koin.core.component.KoinComponent

/**
 * 運行詳細のFakeリポジトリ
 */
class FakeStatusDetailRepository : StatusDetailRepository, KoinComponent {
    override fun fetchStatusDetail(company: Company, portCode: String): Flow<PortStatus> = flow {
        delay(1000)
        val portStatus = PortStatus(
            portCode = portCode,
            portName = "ダミー",
            comment = "コメントコメント",
            status = Status(
                text = "通常運航",
                code = "normal",
            ),
        )
        emit(portStatus)
    }

    override fun fetchTimeTable(company: Company, portCode: String): Flow<TimeTable> = flow {
        delay(1000)
        val rowItem = RowItem(
            status = Status(
                text = "通常運航",
                code = "normal",
            ),
            time = "00:00",
            memo = "メモ"
        )
        val timeTable = TimeTable(
            header = Header(left = "left", right = "right"),
            row = listOf(
                Row(
                    left = rowItem,
                    right = rowItem
                )
            )
        )
        emit(timeTable)
    }
}