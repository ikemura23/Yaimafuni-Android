package com.ikmr.banbara23.yaeyama_liner_checker.front.status.detail;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ikmr.banbara23.yaeyama_liner_checker.front.base.Presenter;
import com.ikmr.banbara23.yaeyama_liner_checker.model.Company;
import com.ikmr.banbara23.yaeyama_liner_checker.model.PortStatus;

import static android.content.ContentValues.TAG;

/**
 * StatusDetailPresenter
 */
public class StatusDetailPresenter implements Presenter<StatusDetailView> {
    private StatusDetailViewModel viewModel;
    private StatusDetailView view;
    private Company company;
    private String portCode;

    public StatusDetailPresenter(StatusDetailViewModel viewModel, Company company, String portCode) {
        this.viewModel = viewModel;
        this.company = company;
        this.portCode = portCode;
    }

    @Override
    public void attachView(StatusDetailView view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
    }

    protected void loadPortDetail() {
        String path = getTablePath();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(path);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                PortStatus portStatus = dataSnapshot.getValue(PortStatus.class);
                setViewModel(portStatus);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    private void setViewModel(PortStatus portStatus) {
        viewModel.portStatus.set(portStatus);
        Log.d(TAG, portStatus.toString());
    }

    public String getTablePath() {
        return company.getCode() + "/" + portCode;
    }
}
