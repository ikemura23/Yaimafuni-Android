package com.yaeyama.linerchecker.domain.repository

import com.yaeyama.linerchecker.domain.statusdetail.Company
import com.yaeyama.linerchecker.domain.statusdetail.PortStatus
import com.yaeyama.linerchecker.domain.timetable.TimeTable
import kotlinx.coroutines.flow.Flow

interface StatusDetailRepository {
    fun fetchStatusDetail(company: Company, portCode: String): Flow<PortStatus>
    fun fetchTimeTable(company: Company, portCode: String): Flow<TimeTable>
}
