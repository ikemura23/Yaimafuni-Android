package com.ikmr.banbara23.yaeyama_liner_checker.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PortStatus(
    val comment: String = "",
    val portCode: String = "",
    val portName: String = "",
    val status: Status = Status()
) : Parcelable
