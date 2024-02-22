package com.yaeyama.linerchecker.repository

import com.yaeyama_liner_checker.domain.statusdetail.Company
import com.yaeyama_liner_checker.domain.statusdetail.PortStatus
import com.yaeyama_liner_checker.domain.time_table.TimeTable
import kotlinx.coroutines.flow.Flow

interface StatusDetailRepository {
    fun fetchStatusDetail(company: Company, portCode: String): Flow<UiState<PortStatus>>
    fun fetchTimeTable(company: Company, portCode: String): Flow<UiState<TimeTable>>
}