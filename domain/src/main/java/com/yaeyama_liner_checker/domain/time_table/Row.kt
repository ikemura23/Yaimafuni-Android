package com.yaeyama_liner_checker.domain.time_table

data class Row(
    val left: RowItem = RowItem(),
    val right: RowItem = RowItem(),
)
