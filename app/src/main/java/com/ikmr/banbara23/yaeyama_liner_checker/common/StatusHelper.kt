package com.ikmr.banbara23.yaeyama_liner_checker.common

import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.ikmr.banbara23.yaeyama_liner_checker.R
import com.ikmr.banbara23.yaeyama_liner_checker.core.Base

object StatusHelper {

    @JvmStatic
    fun getStatusColor(code: String?): Drawable? {
        return when (code) {
            "normal" -> ContextCompat.getDrawable(Base.getContext(), R.drawable.shape_rounded_corners_status_nomal)
            "cancel", "suspend" -> ContextCompat.getDrawable(Base.getContext(), R.drawable.shape_rounded_corners_status_cancel)
            "cation" -> ContextCompat.getDrawable(Base.getContext(), R.drawable.shape_rounded_corners_status_cation)
            else -> ContextCompat.getDrawable(Base.getContext(), R.drawable.shape_rounded_corners_status_cancel)
        }
    }
}

