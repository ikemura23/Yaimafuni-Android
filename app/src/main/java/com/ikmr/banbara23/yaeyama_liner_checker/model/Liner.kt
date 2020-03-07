package com.ikmr.banbara23.yaeyama_liner_checker.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Liner(
    val company: Company,
    val port: Port,
    val status: Status,
    val text: String
) : Parcelable
