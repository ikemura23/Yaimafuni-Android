package com.ikmr.banbara23.yaeyama_liner_checker.front.top

import android.content.Context
import com.ikmr.banbara23.yaeyama_liner_checker.front.base.BaseView
import com.ikmr.banbara23.yaeyama_liner_checker.model.Company

/**
 * 港詳細のViewインターフェイス
 */
interface TopView : BaseView {

    fun getContext(): Context

    fun navigateToWeather()

    fun navigateToTyphoon()

    fun navigateToCompanyStatusList(company: Company)

    fun navigateToPortStatusList(portName: String, portCode: String)
}
