package com.ikmr.banbara23.yaeyama_liner_checker.front.status.detail

import com.ikmr.banbara23.yaeyama_liner_checker.front.base.BaseView

/**
 * 港詳細のViewインターフェイス
 */
interface StatusDetailView : BaseView {

    fun openTell(tel: String)

    fun openBrowser(url: String)
}
