package com.ikmr.banbara23.yaeyama_liner_checker.core

import android.content.Context

open class BaseFragment : android.support.v4.app.Fragment() {
    override fun getContext(): Context? {
        return activity!!.applicationContext
    }
}
