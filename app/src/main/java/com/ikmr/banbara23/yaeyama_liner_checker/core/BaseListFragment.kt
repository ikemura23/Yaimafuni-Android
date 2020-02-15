package com.ikmr.banbara23.yaeyama_liner_checker.core

import android.content.Context
import androidx.fragment.app.ListFragment

open class BaseListFragment : ListFragment() {
    override fun getContext(): Context? {
        return activity!!.applicationContext
    }
}
