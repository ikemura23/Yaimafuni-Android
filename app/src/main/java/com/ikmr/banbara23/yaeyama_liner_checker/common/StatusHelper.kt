package com.ikmr.banbara23.yaeyama_liner_checker.common

import android.support.v4.content.ContextCompat

import com.ikmr.banbara23.yaeyama_liner_checker.R
import com.ikmr.banbara23.yaeyama_liner_checker.core.Base

object StatusHelper {

    @JvmStatic
    fun getStatusColor(code: String?): Int {
        if (code == null) {
            return ContextCompat.getColor(Base.getContext(), R.color.status_cation)
        }
        return when (code) {
            "normal" -> ContextCompat.getColor(Base.getContext(), R.color.status_normal)
            "cancel", "suspend" -> ContextCompat.getColor(Base.getContext(), R.color.status_cancel)
            "cation" -> ContextCompat.getColor(Base.getContext(), R.color.status_cation)
            else -> ContextCompat.getColor(Base.getContext(), R.color.status_cation)
        }
    }
}
