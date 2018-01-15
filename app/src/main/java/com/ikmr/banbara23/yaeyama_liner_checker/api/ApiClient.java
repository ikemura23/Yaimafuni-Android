package com.ikmr.banbara23.yaeyama_liner_checker.api;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ikmr.banbara23.yaeyama_liner_checker.model.Company;
import com.ikmr.banbara23.yaeyama_liner_checker.model.CompanyStatus;
import com.ikmr.banbara23.yaeyama_liner_checker.model.DetailLinerInfo;
import com.ikmr.banbara23.yaeyama_liner_checker.model.PortStatus;
import com.ikmr.banbara23.yaeyama_liner_checker.model.StatusDetailRoot;
import com.ikmr.banbara23.yaeyama_liner_checker.model.TopCompanyInfo;
import com.ikmr.banbara23.yaeyama_liner_checker.model.time_table.TimeTable;
import com.ikmr.banbara23.yaeyama_liner_checker.model.weather.WeatherInfo;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function3;

import static android.content.ContentValues.TAG;

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
        return database.getReference(tablePath);
    }

    /**
     * 天気情報
     *
     * @return
     */
    public Single<WeatherInfo> getWeather() {
        final DatabaseReference myRef = getRef(WEATHER);
        return Single.create(new SingleOnSubscribe<WeatherInfo>() {
            @Override
            public void subscribe(@NonNull final SingleEmitter<WeatherInfo> e) throws Exception {
                // APIリクエスト
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        WeatherInfo weatherInfo = dataSnapshot.getValue(WeatherInfo.class);
                        Log.d(TAG, "Value is: " + weatherInfo.toString());
                        e.onSuccess(weatherInfo);
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        Log.w(TAG, "Failed to read value.", error.toException());
                        e.onError(error.toException());
                    }
                });
            }
        });
    }

    /**
     * トップの会社ステータス
     *
     * @return
     */
    public Single<TopCompanyInfo> getTopCompany() {
        final DatabaseReference myRef = getRef(TOP_COMPANY);
        return Single.create(new SingleOnSubscribe<TopCompanyInfo>() {
            @Override
            public void subscribe(@NonNull final SingleEmitter<TopCompanyInfo> e) throws Exception {
                // APIリクエスト
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        TopCompanyInfo topCompanyInfo = dataSnapshot.getValue(TopCompanyInfo.class);
                        Log.d(TAG, "Value is: " + topCompanyInfo.toString());
                        e.onSuccess(topCompanyInfo);
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        Log.w(TAG, "Failed to read value.", error.toException());
                        e.onError(error.toException());
                    }
                });
            }
        });
    }

    public static Observable<StatusDetailRoot> getDetailInfo(Company company, String portCode) {

        return Observable.zip(
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
    public static Observable<PortStatus> getStatusDetail(Company company, String portCode) {
        final String path = company.getCode() + "/" + portCode;
        final DatabaseReference myRef = getRef(path);
        return Single.create(new SingleOnSubscribe<PortStatus>() {
            @Override
            public void subscribe(@NonNull final SingleEmitter<PortStatus> e) throws Exception {
                // APIリクエスト
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        PortStatus data = dataSnapshot.getValue(PortStatus.class);
                        if (data == null) {
                            e.onError(new Exception(path + " api response is Null"));
                            return;
                        }
                        e.onSuccess(data);
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        Log.w(TAG, "Failed to read value.", error.toException());
                        e.onError(error.toException());
                    }
                });
            }
        }).toObservable();
    }

    /**
     * 運行ステータス以外の情報
     *
     * @return
     */
    public static Observable<DetailLinerInfo> getDetailLinerInfo(Company company, String portCode) {
        final String path = company.getCode() + "_liner_info/" + portCode;
        final DatabaseReference myRef = getRef(path);
        return Single.create(new SingleOnSubscribe<DetailLinerInfo>() {
            @Override
            public void subscribe(@NonNull final SingleEmitter<DetailLinerInfo> e) throws Exception {
                // データの 1 回読み取り
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        DetailLinerInfo data = dataSnapshot.getValue(DetailLinerInfo.class);
                        if (data == null) {
                            e.onError(new Exception(path + " api response is Null"));
                            return;
                        }
                        Log.d(TAG, "Value is: " + data.toString());
                        e.onSuccess(data);
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        Log.w(TAG, "Failed to read value.", error.toException());
                        e.onError(error.toException());
                    }
                });
            }
        }).toObservable();
    }

    /**
     * 時間毎の運行ステータス
     *
     * @return
     */
    public static Observable<TimeTable> getTimeTable(Company company, String portCode) {
        final String path = company.getCode() + "_timeTable/" + portCode;
        final DatabaseReference myRef = getRef(path);
        return Single.create(new SingleOnSubscribe<TimeTable>() {
            @Override
            public void subscribe(@NonNull final SingleEmitter<TimeTable> e) throws Exception {
                // データの 1 回読み取り
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        TimeTable data = dataSnapshot.getValue(TimeTable.class);
                        if (data == null) {
                            e.onError(new Exception(path + " api response is Null"));
                            return;
                        }
                        Log.d(TAG, "Value is: " + data.toString());
                        e.onSuccess(data);
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        Log.w(TAG, "Failed to read value.", error.toException());
                        e.onError(error.toException());
                    }
                });
            }
        }).toObservable();
    }

    /**
     * 会社別の運行ステータス一覧（anei ,ykf, dream of list)
     *
     * @param path
     * @return
     */
    public Single<CompanyStatus> getCompanyStatus(final String path) {
        final DatabaseReference myRef = getRef(path);
        return Single.create(new SingleOnSubscribe<CompanyStatus>() {
            @Override
            public void subscribe(@NonNull final SingleEmitter<CompanyStatus> e) throws Exception {
                // データの 1 回読み取り
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        CompanyStatus data = dataSnapshot.getValue(CompanyStatus.class);
                        if (data == null) {
                            e.onError(new Exception(path + " api response is Null"));
                            return;
                        }
                        Log.d(TAG, "Value is: " + data.toString());
                        e.onSuccess(data);
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        Log.w(TAG, "Failed to read value.", error.toException());
                        e.onError(error.toException());
                    }
                });
            }
        });
    }
}
