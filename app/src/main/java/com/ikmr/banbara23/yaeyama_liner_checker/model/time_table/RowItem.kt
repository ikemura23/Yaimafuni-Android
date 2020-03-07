package com.ikmr.banbara23.yaeyama_liner_checker.model.time_table

import android.os.Parcelable
import com.ikmr.banbara23.yaeyama_liner_checker.model.Status
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RowItem(
    val memo: String = "",
    val status: Status = Status(),
    val time: String = ""
) : Parcelable
