package com.ikemura.shared.model.time_table

import kotlinx.serialization.Serializable

@Serializable
data class Row(
    val left: RowItem = RowItem(),
    val right: RowItem = RowItem()
)
