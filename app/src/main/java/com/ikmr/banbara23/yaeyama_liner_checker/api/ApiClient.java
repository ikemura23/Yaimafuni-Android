package com.ikmr.banbara23.yaeyama_liner_checker.api;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ikmr.banbara23.yaeyama_liner_checker.model.DetailLinerInfo;
import com.ikmr.banbara23.yaeyama_liner_checker.model.PortStatus;
import com.ikmr.banbara23.yaeyama_liner_checker.model.TopCompanyInfo;
import com.ikmr.banbara23.yaeyama_liner_checker.model.weather.WeatherInfo;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.annotations.NonNull;

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
    private DatabaseReference getRef(String tablePath) {
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
                myRef.addValueEventListener(new ValueEventListener() {
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
                myRef.addValueEventListener(new ValueEventListener() {
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

    /**
     * 運行詳細のステータス
     *
     * @param path
     * @return
     */
    public Single<PortStatus> getStatusDetail(String path) {
        final DatabaseReference myRef = getRef(path);
        return Single.create(new SingleOnSubscribe<PortStatus>() {
            @Override
            public void subscribe(@NonNull final SingleEmitter<PortStatus> e) throws Exception {
                // APIリクエスト
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        PortStatus data = dataSnapshot.getValue(PortStatus.class);
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

    /**
     * 運行ステータス以外の情報
     *
     * @param port
     * @return
     */
    public Single<DetailLinerInfo> getDetailLinerInfo(String port) {
        final DatabaseReference myRef = getRef(port);
        return Single.create(new SingleOnSubscribe<DetailLinerInfo>() {
            @Override
            public void subscribe(@NonNull final SingleEmitter<DetailLinerInfo> e) throws Exception {
                // APIリクエスト
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        DetailLinerInfo data = dataSnapshot.getValue(DetailLinerInfo.class);
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
