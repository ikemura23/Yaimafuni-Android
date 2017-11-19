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

    StatusDetailPresenter(StatusDetailViewModel viewModel, Company company, String portCode) {
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

    /**
     * ViewModelにセット
     *
     * @param portStatus
     */
    private void setViewModel(PortStatus portStatus) {
        Log.d(TAG, portStatus.toString());
        // ViewModelにセット
        viewModel.portStatus.set(portStatus);
        // 画像セット
        viewModel.setStatusDrawable(portStatus.getStatus().getCode());
    }

    public String getTablePath() {
        return company.getCode() + "/" + portCode;
    }

    /**
     * 外部電話アプリを起動
     */
    public void startTel() {
        view.openTell("00000");
    }

    /**
     * 外部ブラウザを起動
     */
    public void startWeb() {
        view.openBrowser("");
    }
}
