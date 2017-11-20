package com.ikmr.banbara23.yaeyama_liner_checker.front.top;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ikmr.banbara23.yaeyama_liner_checker.front.base.Presenter;
import com.ikmr.banbara23.yaeyama_liner_checker.model.TopCompanyInfo;

import static android.content.ContentValues.TAG;

/**
 * トップ画面のPresenter
 */
public class TopPresenter implements Presenter<TopView> {
    private TopViewModel viewModel;
    private TopView view;

    /**
     * コンストラクタ
     *
     * @param view
     * @param viewModel
     */
    TopPresenter(TopView view, TopViewModel viewModel) {
        this.view = view;
        this.viewModel = viewModel;
    }

    @Override
    public void attachView(TopView view) {
        this.view = view;
        view.showProgressBar();
    }

    @Override
    public void detachView() {
        view = null;
    }

    protected void fetchTopStatus() {
        String path = "top_company";
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(path);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                TopCompanyInfo topCompanyInfo = dataSnapshot.getValue(TopCompanyInfo.class);
                Log.d(TAG, "Value is: " + topCompanyInfo.toString());
                onComplete(topCompanyInfo);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    protected void fetchWeather() {
        String path = "top_company";
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(path);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                TopCompanyInfo topCompanyInfo = dataSnapshot.getValue(TopCompanyInfo.class);
                Log.d(TAG, "Value is: " + topCompanyInfo.toString());
                onComplete(topCompanyInfo);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    /**
     * 通信が完了
     *
     * @param topCompanyInfo
     */
    private void onComplete(TopCompanyInfo topCompanyInfo) {
        viewModel.topCompany.set(topCompanyInfo);
        setAneiStatus(topCompanyInfo.getAnei());
        setYkfStatus(topCompanyInfo.getYkf());
        setDreamStatus(topCompanyInfo.getDream());
        view.hideProgressBar();
    }

    private void setAneiStatus(TopCompanyInfo.TopCompany anei) {
        // TODO: 2017/11/21 ステータス表示を制御
    }

    private void setYkfStatus(TopCompanyInfo.TopCompany ykf) {
        // TODO: 2017/11/21 ステータス表示を制御
    }

    private void setDreamStatus(TopCompanyInfo.TopCompany dream) {
        // TODO: 2017/11/21 ステータス表示を制御
    }
}
