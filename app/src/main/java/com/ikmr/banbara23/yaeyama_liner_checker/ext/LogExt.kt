package com.ikmr.banbara23.yaeyama_liner_checker.ext

import timber.log.Timber

fun Any.logV(log: Any) = Timber.tag(this::class.java.name).v(log.toString())

fun Any.logD(log: Any) = Timber.tag(this::class.java.name).d(log.toString())

fun Any.logI(log: Any) = Timber.tag(this::class.java.name).i(log.toString())

fun Any.logW(log: Any) = Timber.tag(this::class.java.name).w(log.toString())

fun Any.logE(log: Any) = Timber.tag(this::class.java.name).e(log.toString())
