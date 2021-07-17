package com.ikemura.shared.model.time_table

import com.ikemura.shared.model.statusdetail.Status
import kotlinx.serialization.Serializable

@Serializable
data class RowItem(
    val memo: String = "",
    val status: Status = Status(),
    val time: String = ""
)
