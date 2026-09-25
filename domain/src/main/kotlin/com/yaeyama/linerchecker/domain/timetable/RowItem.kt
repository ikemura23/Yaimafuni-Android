package com.yaeyama.linerchecker.domain.timetable

import com.yaeyama.linerchecker.domain.statusdetail.Status

data class RowItem(
    val memo: String = "",
    val status: Status = Status(),
    val time: String = "",
)
