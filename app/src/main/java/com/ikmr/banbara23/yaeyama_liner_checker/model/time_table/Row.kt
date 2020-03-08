package com.ikmr.banbara23.yaeyama_liner_checker.model.time_table

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Row(
    val left: RowItem = RowItem(),
    val right: RowItem = RowItem()
) : Parcelable
