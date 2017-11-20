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
 * StatusDetailPresenter
 */
public class TopPresenter implements Presenter<TopView> {
    private TopViewModel viewModel;
    private TopView view;

    TopPresenter(TopView view, TopViewModel viewModel) {
        this.view = view;
        this.viewModel = viewModel;
    }

    @Override
    public void attachView(TopView view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
    }

    protected void fetchData() {
        String path = getTablePath();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(path);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                TopCompanyInfo topCompanyInfo = dataSnapshot.getValue(TopCompanyInfo.class);
                setViewModel(topCompanyInfo);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    /**
     * ViewModelにセット
     *
     * @param topCompanyInfo
     */
    private void setViewModel(TopCompanyInfo topCompanyInfo) {
        Log.d(TAG, "Value is: " + topCompanyInfo.toString());
        // ViewModelにセット
        viewModel.topCompany.set(topCompanyInfo);
    }

    public String getTablePath() {
        return "top_company";
    }
}
