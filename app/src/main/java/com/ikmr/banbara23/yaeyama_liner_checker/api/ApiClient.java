package com.ikmr.banbara23.yaeyama_liner_checker.api;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ikmr.banbara23.yaeyama_liner_checker.model.Company;
import com.ikmr.banbara23.yaeyama_liner_checker.model.CompanyStatus;
import com.ikmr.banbara23.yaeyama_liner_checker.model.DetailLinerInfo;
import com.ikmr.banbara23.yaeyama_liner_checker.model.PortStatus;
import com.ikmr.banbara23.yaeyama_liner_checker.model.StatusDetailRoot;
import com.ikmr.banbara23.yaeyama_liner_checker.model.TopCompanyInfo;
import com.ikmr.banbara23.yaeyama_liner_checker.model.time_table.TimeTable;
import com.ikmr.banbara23.yaeyama_liner_checker.model.weather.WeatherInfo;

import durdinapps.rxfirebase2.RxFirebaseDatabase;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function3;
import io.reactivex.schedulers.Schedulers;

/**
 * APIクライアント
 */
public class ApiClient {

    private static final String WEATHER = "weather";
    private static final String TOP_COMPANY = "top_company";

    public ApiClient() {
    }

    /**
     * DBの参照を作成して返す
     *
     * @param tablePath テーブルのパス
     * @return
     */
    private static DatabaseReference getRef(String tablePath) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference(tablePath);
        return reference;
    }

    /**
     * 天気情報
     *
     * @return
     */
    public Flowable<WeatherInfo> getWeather() {
        final DatabaseReference ref = getRef(WEATHER);
        ref.keepSynced(false);
        return RxFirebaseDatabase.observeValueEvent(ref, WeatherInfo.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * トップの会社ステータス
     *
     * @return
     */
    public Flowable<TopCompanyInfo> getTopCompany() {
        final DatabaseReference ref = getRef(TOP_COMPANY);
        ref.keepSynced(false);
        return RxFirebaseDatabase.observeValueEvent(ref, TopCompanyInfo.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Flowable<StatusDetailRoot> getDetailInfo(Company company, String portCode) {

        return Flowable.zip(
                // ステータスのみ
                getStatusDetail(company, portCode),
                // 運行関連（走行時間、金額など）
                getDetailLinerInfo(company, portCode),
                // 時間別の運行ステータス
                getTimeTable(company, portCode),
                new Function3<PortStatus, DetailLinerInfo, TimeTable, StatusDetailRoot>() {
                    @Override
                    public StatusDetailRoot apply(
                            PortStatus portStatus,
                            DetailLinerInfo detailLinerInfo,
                            TimeTable timeTable) throws Exception {
                        return new StatusDetailRoot(portStatus, detailLinerInfo, timeTable);
                    }
                });
    }

    /**
     * 運行詳細のステータス
     *
     * @return
     */
    public static Flowable<PortStatus> getStatusDetail(Company company, String portCode) {
        final String path = company.getCode() + "/" + portCode;
        final DatabaseReference ref = getRef(path);
        ref.keepSynced(false);
        return RxFirebaseDatabase.observeValueEvent(ref, PortStatus.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 運行ステータス以外の情報
     *
     * @return
     */
    public static Flowable<DetailLinerInfo> getDetailLinerInfo(Company company, String portCode) {
        final String path = company.getCode() + "_liner_info/" + portCode;
        final DatabaseReference ref = getRef(path);
        ref.keepSynced(false);
        return RxFirebaseDatabase.observeValueEvent(ref, DetailLinerInfo.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 時間毎の運行ステータス
     *
     * @return
     */
    public static Flowable<TimeTable> getTimeTable(Company company, String portCode) {
        final String path = company.getCode() + "_timeTable/" + portCode;
        final DatabaseReference ref = getRef(path);
        ref.keepSynced(false);
        return RxFirebaseDatabase.observeValueEvent(ref, TimeTable.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 会社別の運行ステータス一覧（anei ,ykf, dream of list)
     *
     * @param path
     * @return
     */
    public Flowable<CompanyStatus> getCompanyStatus(final String path) {
        final DatabaseReference ref = getRef(path);
        ref.keepSynced(false);
        return RxFirebaseDatabase.observeValueEvent(ref, CompanyStatus.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
