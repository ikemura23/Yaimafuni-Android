package com.ikmr.banbara23.yaeyama_liner_checker.ui.companylist

import com.airbnb.epoxy.TypedEpoxyController
import com.ikmr.banbara23.yaeyama_liner_checker.fragmentLinerListView
import com.ikmr.banbara23.yaeyama_liner_checker.fragmentStatusListHeaderView
import com.ikmr.banbara23.yaeyama_liner_checker.rowDelimiter

class CompanyListEpoxyController : TypedEpoxyController<CompanyListUIData>() {
    override fun buildModels(data: CompanyListUIData) {
        // ヘッダー
        fragmentStatusListHeaderView {
            id("header")
            title(data.comment)
            updateTime(data.updateTime)
        }
        // 運行情報一覧
        data.list.forEach { portStatus ->
            // 運行情報
            fragmentLinerListView {
                id(portStatus.hashCode())
                portStatus(portStatus)
                showableComment(portStatus.comment.isNotEmpty())
                onItemViewClick { model, _, _, _ ->
                    data.onClickHandler(model.portStatus())
                }
            }
            // 区切り線
            rowDelimiter {
                id("row")
            }
        }
    }
}
