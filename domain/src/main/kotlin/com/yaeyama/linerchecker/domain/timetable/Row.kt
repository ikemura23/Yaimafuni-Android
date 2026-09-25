package com.yaeyama.linerchecker.domain.timetable

data class Row(
    val left: RowItem = RowItem(),
    val right: RowItem = RowItem(),
)
