package com.ikmr.banbara23.yaeyama_liner_checker.common

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager

class CustomLinearLayoutManager(context: Context) :
    LinearLayoutManager(context) {
    override fun canScrollVertically(): Boolean {
        return false
    }
}
