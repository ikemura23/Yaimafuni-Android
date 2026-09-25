package com.yaeyama.linerchecker.repository

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.getValue
import com.yaeyama.linerchecker.ext.valueEvents
import com.yaeyama_liner_checker.domain.repository.StatusDetailRepository
import com.yaeyama_liner_checker.domain.statusdetail.Company
import com.yaeyama_liner_checker.domain.statusdetail.PortStatus
import com.yaeyama_liner_checker.domain.time_table.TimeTable
import kotlinx.coroutines.flow.Flow

class StatusDetailRepositoryImpl(
    private val database: FirebaseDatabase,
) : StatusDetailRepository {

    /**
     * 運行情報の詳細を取得する
     */
    override fun fetchStatusDetail(company: Company, portCode: String): Flow<PortStatus> =
        database.valueEvents("${company.code}/$portCode") { snapshot ->
            snapshot.getValue<PortStatus>()
        }

    override fun fetchTimeTable(company: Company, portCode: String): Flow<TimeTable> =
        database.valueEvents("${company.code}_timeTable/$portCode") { snapshot ->
            snapshot.getValue<TimeTable>()
        }
}
