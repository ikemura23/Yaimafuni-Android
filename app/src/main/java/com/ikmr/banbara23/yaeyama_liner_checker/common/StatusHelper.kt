package com.ikmr.banbara23.yaeyama_liner_checker.common

import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter

import com.ikmr.banbara23.yaeyama_liner_checker.R
import com.ikmr.banbara23.yaeyama_liner_checker.core.Base

object StatusHelper {

    @JvmStatic
    fun getStatusColor(code: String?): Drawable? {
        // if (code == null) {
        //     return ContextCompat.getDrawable(
        //         Base.getContext(), R.drawable.shape_rounded_corners_status_cation
        //     )
        // }
        return when (code) {
            "normal" -> ContextCompat.getDrawable(Base.getContext(), R.drawable.shape_rounded_corners_status_nomal)
            "cancel", "suspend" -> ContextCompat.getDrawable(Base.getContext(), R.drawable.shape_rounded_corners_status_cancel)
            "cation" -> ContextCompat.getDrawable(Base.getContext(), R.drawable.shape_rounded_corners_status_cation)
            else -> ContextCompat.getDrawable(Base.getContext(), R.drawable.shape_rounded_corners_status_cancel)
        }
    }
}

@BindingAdapter("statusBackground")
fun setStatusBackground(view: TextView, statusCode: String?) {
    statusCode ?: run {
        Log.d("statusBackground", "statusCode is null")
        return
    }
    Log.d("statusBackground", statusCode)
}

