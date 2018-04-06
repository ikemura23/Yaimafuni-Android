package com.ikmr.banbara23.yaeyama_liner_checker.front.base

/**
 * Presenterのベース
 */
interface Presenter<V> {

    fun attachView(view: V)

    fun detachView()

}
