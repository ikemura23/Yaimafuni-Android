package com.ikmr.banbara23.yaeyama_liner_checker.model.time_table

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TimeTable(
    val header: Header = Header(),
    val row: List<Row> = listOf()
) : Parcelable
