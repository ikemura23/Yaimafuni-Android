package com.ikmr.banbara23.yaeyama_liner_checker.ext

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat

fun Context.getDrawableCompat(@DrawableRes id: Int): Drawable? = ContextCompat.getDrawable(this, id)
