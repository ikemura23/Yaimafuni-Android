package com.yaeyama_liner_checker.domain.time_table

import kotlinx.serialization.Serializable

@Serializable
data class Row(
    val left: RowItem = RowItem(),
    val right: RowItem = RowItem()
)
