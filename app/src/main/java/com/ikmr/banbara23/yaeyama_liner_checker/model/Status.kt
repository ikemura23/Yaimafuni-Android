package com.ikmr.banbara23.yaeyama_liner_checker.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Status(
    val code: String = "",
    val text: String = ""
) : Parcelable
