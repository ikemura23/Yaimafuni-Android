package com.ikmr.banbara23.yaeyama_liner_checker.model.time_table

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Header(
    val left: String = "",
    val right: String = ""
) : Parcelable
