package com.ikmr.banbara23.yaeyama_liner_checker.core

import android.content.Context
import android.support.v4.app.ListFragment

open class BaseListFragment : ListFragment() {
    override fun getContext(): Context? {
        return activity!!.applicationContext
    }
}
