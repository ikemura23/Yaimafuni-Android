package com.yaeyama.linerchecker.usecase

import com.yaeyama_liner_checker.domain.repository.StatusDetailRepository
import com.yaeyama_liner_checker.domain.statusdetail.Company
import com.yaeyama_liner_checker.domain.statusdetail.PortStatus
import com.yaeyama_liner_checker.domain.statusdetail.StatusDetailResult
import com.yaeyama_liner_checker.domain.time_table.TimeTable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.zip

class GetStatusDetail(
    private val statusDetailRepository: StatusDetailRepository,
) {
    suspend operator fun invoke(company: Company, portCode: String): Flow<StatusDetailResult> {
        val portStatusFlow = statusDetailRepository.fetchStatusDetail(company, portCode)
        val timeTableFlow = statusDetailRepository.fetchTimeTable(company, portCode)
        return portStatusFlow.zip(timeTableFlow) { portStatus: PortStatus, timeTable: TimeTable ->
            StatusDetailResult(
                portStatus,
                timeTable,
            )
        }
            // return@zip if (portStatusState is UiState.Success && timeTableState is UiState.Success) {
            //     val result = StatusDetailResult(
            //         portStatusState.data,
            //         timeTableState.data,
            //     )
            //     UiState.Success(result)
            // } else {
            //     UiState.Error(Exception("運行詳細データの取得でエラーが発生"))
            // }
    }
}
