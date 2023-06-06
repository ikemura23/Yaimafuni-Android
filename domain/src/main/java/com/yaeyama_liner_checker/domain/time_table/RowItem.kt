package com.yaeyama_liner_checker.domain.time_table

import com.yaeyama_liner_checker.domain.statusdetail.Status

data class RowItem(
    val memo: String = "",
    val status: Status = Status(),
    val time: String = "",
)
