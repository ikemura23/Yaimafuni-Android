package com.ikmr.banbara23.yaeyama_liner_checker.front.top

import com.ikmr.banbara23.yaeyama_liner_checker.front.base.BaseView
import com.ikmr.banbara23.yaeyama_liner_checker.model.Company

/**
 * 港詳細のViewインターフェイス
 */
interface TopView : BaseView {

    fun navigateToWeather()

    fun navigateToCompanyStatusList(company: Company)

    fun navigateToPortStatusList(port: String)
}
