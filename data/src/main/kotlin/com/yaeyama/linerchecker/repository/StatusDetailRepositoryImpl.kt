package com.yaeyama.linerchecker.repository

import com.google.firebase.database.FirebaseDatabase
import com.yaeyama.linerchecker.domain.repository.StatusDetailRepository
import com.yaeyama.linerchecker.domain.statusdetail.Company
import com.yaeyama.linerchecker.domain.statusdetail.PortStatus
import com.yaeyama.linerchecker.domain.timetable.TimeTable
import com.yaeyama.linerchecker.ext.valueEventsOf
import kotlinx.coroutines.flow.Flow

class StatusDetailRepositoryImpl(
    private val database: FirebaseDatabase,
) : StatusDetailRepository {

    /**
     * 運行情報の詳細を取得する
     */
    override fun fetchStatusDetail(company: Company, portCode: String): Flow<PortStatus> =
        database.valueEventsOf<PortStatus>("${company.code}/$portCode")

    override fun fetchTimeTable(company: Company, portCode: String): Flow<TimeTable> =
        database.valueEventsOf<TimeTable>("${company.code}_timeTable/$portCode")
}
