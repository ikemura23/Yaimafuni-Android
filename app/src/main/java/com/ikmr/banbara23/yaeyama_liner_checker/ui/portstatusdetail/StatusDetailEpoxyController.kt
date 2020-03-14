package com.ikmr.banbara23.yaeyama_liner_checker.ui.portstatusdetail

import com.airbnb.epoxy.TypedEpoxyController
import com.ikmr.banbara23.yaeyama_liner_checker.databinding.ViewStatusDetailActionBinding
import com.ikmr.banbara23.yaeyama_liner_checker.model.StatusDetailRoot
import com.ikmr.banbara23.yaeyama_liner_checker.timeTableHeader
import com.ikmr.banbara23.yaeyama_liner_checker.timeTableRow
import com.ikmr.banbara23.yaeyama_liner_checker.viewSpace
import com.ikmr.banbara23.yaeyama_liner_checker.viewStatusDetailAction
import com.ikmr.banbara23.yaeyama_liner_checker.viewStatusDetailTop

class StatusDetailEpoxyController(private val listener: StatusDetailClickListener?) :
    TypedEpoxyController<StatusDetailRoot>() {

    interface StatusDetailClickListener {
        /**　ブラウザで見るクリック */
        fun onWebClicked(url: String)

        /** 電話するクリック */
        fun onTelClicked(tel: String)
    }

    override fun buildModels(root: StatusDetailRoot) {
        val portStatus = root.portStatus
        val timeTable = root.timeTable
        val linerInfo = root.detailLinerInfo

        // 運行ステータス
        viewStatusDetailTop {
            id(portStatus.hashCode())
            portStatus(portStatus)
        }

        viewSpace {
            id("space1")
        }

        // 時刻表 ヘッダー
        timeTableHeader {
            id("header ${timeTable.header.hashCode()}")
            header(timeTable.header)
        }
        // 時刻表
        timeTable.row.forEach { row ->
            timeTableRow {
                id("row_${row.hashCode()}")
                row(row)
            }
        }
        
        viewSpace {
            id("space1")
        }

        // アクション
        viewStatusDetailAction {
            id("action")
            onBind { _, view, _ ->
                val binding = view.dataBinding as ViewStatusDetailActionBinding
                binding.web.setOnClickListener {
                    listener?.onWebClicked(linerInfo.url)
                }
                binding.tell.setOnClickListener {
                    listener?.onTelClicked(linerInfo.tell)
                }
            }
        }
    }
}
