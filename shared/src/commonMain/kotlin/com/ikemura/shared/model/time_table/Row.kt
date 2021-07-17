package com.ikemura.shared.model.time_table

data class Row(
    val left: RowItem = RowItem(),
    val right: RowItem = RowItem()
)
