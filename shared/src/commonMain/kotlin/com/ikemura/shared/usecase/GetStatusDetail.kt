package com.ikemura.shared.usecase

import com.ikemura.shared.model.statusdetail.Company
import com.ikemura.shared.model.statusdetail.PortStatus
import com.ikemura.shared.model.statusdetail.StatusDetailResult
import com.ikemura.shared.repository.StatusDetailRepository
import com.ikemura.shared.repository.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.zip

class GetStatusDetail(
    private val statusDetailRepository: StatusDetailRepository,
) {
    suspend operator fun invoke(company: Company, portCode: String): Flow<UiState<StatusDetailResult>> {
        val portStatusFlow = statusDetailRepository.fetchStatusDetail(company, portCode)
        val timeTableFlow = statusDetailRepository.fetchTimeTable(company, portCode)
        return portStatusFlow.zip(timeTableFlow) { portStatusState: UiState<PortStatus>, timeTableState ->
            return@zip if (portStatusState is UiState.Success && timeTableState is UiState.Success) {
                val result = StatusDetailResult(
                    portStatusState.data,
                    timeTableState.data
                )
                UiState.Success(result)
            } else {
                UiState.Error(Exception("運行詳細データの取得でエラーが発生"))
            }
        }
    }
}
