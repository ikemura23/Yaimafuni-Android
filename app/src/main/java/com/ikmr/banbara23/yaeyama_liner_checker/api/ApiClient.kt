package com.ikmr.banbara23.yaeyama_liner_checker.api

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.ikmr.banbara23.yaeyama_liner_checker.model.Company
import com.ikmr.banbara23.yaeyama_liner_checker.model.CompanyStatus
import com.ikmr.banbara23.yaeyama_liner_checker.model.DetailLinerInfo
import com.ikmr.banbara23.yaeyama_liner_checker.model.PortStatus
import com.ikmr.banbara23.yaeyama_liner_checker.model.StatusDetailRoot
import com.ikmr.banbara23.yaeyama_liner_checker.model.time_table.TimeTable
import com.ikmr.banbara23.yaeyama_liner_checker.model.top.TopCompanyInfo
import com.ikmr.banbara23.yaeyama_liner_checker.model.top.TopPort
import durdinapps.rxfirebase2.RxFirebaseDatabase
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function3
import io.reactivex.schedulers.Schedulers

/**
 * APIクライアント
 */
class ApiClient {

    /**
     * トップの会社ステータス
     *
     * @return
     */
    val topCompany: Flowable<TopCompanyInfo>
        get() {
            val ref = getRef(TOP_COMPANY)
            ref.keepSynced(false)
            return RxFirebaseDatabase.observeValueEvent(ref, TopCompanyInfo::class.java)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }

    /**
     * トップの会社ステータス
     *
     * @return
     */
    val topPortStatus: Flowable<TopPort>
        get() {
            val ref = getRef(TOP_PORT)
            ref.keepSynced(false)
            return RxFirebaseDatabase.observeValueEvent(ref, TopPort::class.java)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }

    /**
     * 会社別の運行ステータス一覧（anei ,ykf, dream of list)
     *
     * @param path
     * @return
     */
    fun getCompanyStatus(path: String): Flowable<CompanyStatus> {
        val ref = getRef(path)
        ref.keepSynced(false)
        return RxFirebaseDatabase.observeValueEvent(ref, CompanyStatus::class.java)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getDetailInfo(company: Company, portCode: String): Flowable<StatusDetailRoot> {
        return Flowable.zip(
            // ステータスのみ
            getStatusDetail(company, portCode),
            // 運行関連（走行時間、金額など）
            getDetailLinerInfo(company, portCode),
            // 時間別の運行ステータス
            getTimeTable(company, portCode),
            Function3(::StatusDetailRoot)
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    companion object {

        private const val WEATHER = "weather"
        private const val TYPHOON = "typhoon/tenkijp"
        private const val TOP_COMPANY = "top_company"
        private const val TOP_PORT = "top_port"

        /**
         * DBの参照を作成して返す
         *
         * @param tablePath テーブルのパス
         * @return
         */
        private fun getRef(tablePath: String): DatabaseReference {
            val database = FirebaseDatabase.getInstance()
            return database.getReference(tablePath)
        }

        /**
         * 運行詳細のステータス
         *
         * @return
         */
        fun getStatusDetail(company: Company, portCode: String): Flowable<PortStatus> {
            val path = company.code + "/" + portCode
            val ref = getRef(path)
            ref.keepSynced(false)
            return RxFirebaseDatabase.observeValueEvent(ref, PortStatus::class.java)
        }

        /**
         * 運行ステータス以外の情報
         *
         * @return
         */
        fun getDetailLinerInfo(company: Company, portCode: String): Flowable<DetailLinerInfo> {
            val path = company.code + "_liner_info/" + portCode
            val ref = getRef(path)
            ref.keepSynced(false)
            return RxFirebaseDatabase.observeValueEvent(ref, DetailLinerInfo::class.java)
        }

        /**
         * 時間毎の運行ステータス
         *
         * @return
         */
        fun getTimeTable(company: Company, portCode: String): Flowable<TimeTable> {
            val path = company.code + "_timeTable/" + portCode
            val ref = getRef(path)
            ref.keepSynced(false)
            return RxFirebaseDatabase.observeValueEvent(ref, TimeTable::class.java)
        }
    }
}
