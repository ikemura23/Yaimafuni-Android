package com.yaeyama.linerchecker.repository

import com.yaeyama.linerchecker.domain.repository.StatusDetailRepository
import com.yaeyama.linerchecker.domain.statusdetail.Company
import com.yaeyama.linerchecker.domain.statusdetail.PortStatus
import com.yaeyama.linerchecker.domain.statusdetail.Status
import com.yaeyama.linerchecker.domain.timetable.Header
import com.yaeyama.linerchecker.domain.timetable.Row
import com.yaeyama.linerchecker.domain.timetable.RowItem
import com.yaeyama.linerchecker.domain.timetable.TimeTable
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * 運行詳細のFakeリポジトリ
 */
class FakeStatusDetailRepository : StatusDetailRepository {
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
