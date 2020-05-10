package com.ikmr.banbara23.yaeyama_liner_checker.ui.companylist

import com.airbnb.epoxy.TypedEpoxyController
import com.ikmr.banbara23.yaeyama_liner_checker.fragmentLinerListView
import com.ikmr.banbara23.yaeyama_liner_checker.fragmentStatusListHeaderView
import com.ikmr.banbara23.yaeyama_liner_checker.model.CompanyStatus
import com.ikmr.banbara23.yaeyama_liner_checker.model.PortStatus
import com.ikmr.banbara23.yaeyama_liner_checker.rowDelimiter

class CompanyListEpoxyController : TypedEpoxyController<CompanyStatus>() {
    override fun buildModels(data: CompanyStatus) {
        // ヘッダー
        fragmentStatusListHeaderView {
            id("header")
            title(data.comment)
            updateTime(data.updateTime)
        }
        // 運行情報一覧
        val list = convertToList(data)
        list.forEach { portStatus ->
            // 運行情報
            fragmentLinerListView {
                id(portStatus.hashCode())
                portStatus(portStatus)
                showableComment(portStatus.comment.isNotEmpty())
            }
            // 区切り線
            rowDelimiter {
                id("row")
            }
        }
    }

    /**
     * 運行情報を配列にする
     */
    private fun convertToList(companyStatus: CompanyStatus): List<PortStatus> {
        val list = arrayListOf<PortStatus>(
            companyStatus.taketomi,
            companyStatus.kohama,
            companyStatus.kuroshima,
            companyStatus.oohara,
            companyStatus.uehara
        )
        return list.also {
            companyStatus.hateruma?.let { hateruma ->
                it.add(hateruma)
            }
        }.toList()
    }
}
