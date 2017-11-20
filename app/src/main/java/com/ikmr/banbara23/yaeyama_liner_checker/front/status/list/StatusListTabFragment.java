
package com.ikmr.banbara23.yaeyama_liner_checker.front.status.list;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.common.Constants;
import com.ikmr.banbara23.yaeyama_liner_checker.core.BaseListFragment;
import com.ikmr.banbara23.yaeyama_liner_checker.front.status.detail.StatusDetailActivity;
import com.ikmr.banbara23.yaeyama_liner_checker.model.Company;
import com.ikmr.banbara23.yaeyama_liner_checker.model.CompanyStatus;
import com.ikmr.banbara23.yaeyama_liner_checker.model.PortStatus;
import com.ikmr.banbara23.yaeyama_liner_checker.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 一覧タブListFragment
 */
public class StatusListTabFragment extends BaseListFragment {

    private static final String TAG = StatusListTabFragment.class.getSimpleName();
    StatusListAdapter mListAdapter;
    TextView mTitleText;
    TextView mUpdateText;
    View mHeaderView;

//    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    /**
     * リロードタップ
     *
     * @param view リロードボタン
     */
    void emptyClick(View view) {
        if (getActivity() != null) {
            ((EmptyClickListener) getActivity()).emptyClick();
        }
//        AnalyticsUtils.logSelectEvent(TAG, "reload");
    }

    public interface EmptyClickListener {
        void emptyClick();
    }

    public static StatusListTabFragment NewInstance(Company company) {
        StatusListTabFragment fragment = new StatusListTabFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(StatusListTabFragment.class.getCanonicalName(), company);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_status_list, container, false);
        initViews();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        startQuery();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getListView().addHeaderView(mHeaderView, null, false);
    }

    private void initViews() {
        mHeaderView = View.inflate(getActivity(), R.layout.fragment_status_list_header_view, null);
        mTitleText = (TextView) mHeaderView.findViewById(R.id.fragment_status_list_toolbar_title_text);
        mUpdateText = (TextView) mHeaderView.findViewById(R.id.fragment_status_list_toolbar_update_text);
        mListAdapter = new StatusListAdapter(getContext());
    }

    /**
     * パラメータ取得
     *
     * @return
     */
    private Company getCompany() {
        return (Company) getArguments().get(StatusListTabFragment.class.getCanonicalName());
    }

    private void startQuery() {
        mHeaderView.setVisibility(View.GONE);
        mListAdapter.clear();
        setListAdapter(mListAdapter);

        String table = "";
        switch (getCompany()) {
            case ANEI:
                table = "anei";
                break;
            case YKF:
                table = "ykf";
                break;
            case DREAM:
                table = "dream";
                break;
        }
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(table);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                CompanyStatus companyStatus = dataSnapshot.getValue(CompanyStatus.class);
                onResultListQuery(companyStatus);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    /**
     * 更新時間
     *
     * @param update
     */
    private void setUpdate(String update) {
        if (TextUtils.isEmpty(update)) {
            mUpdateText.setVisibility(View.GONE);
            return;
        }
        mUpdateText.setText(update);
    }

    /**
     * タイトル
     *
     * @param title
     */
    private void setTitle(String title) {
        if (TextUtils.isEmpty(title)) {
            mTitleText.setVisibility(View.GONE);
            return;
        }

        String replaceTitle = StringUtils.replacePunctuation(StringUtils.replaceSpaceJ(title));
        mTitleText.setText(replaceTitle);
    }

    private void onResultListQuery(CompanyStatus companyStatus) {
        if (companyStatus == null) {
            return;
        }
        mHeaderView.setVisibility(View.VISIBLE);
        setTitle(companyStatus.getComment());
        setUpdate(companyStatus.getUpdateTime());

        List<PortStatus> portStatuses = new ArrayList<>();
        portStatuses.add(companyStatus.getKohama());
        portStatuses.add(companyStatus.getTaketomi());
        portStatuses.add(companyStatus.getKuroshima());
        portStatuses.add(companyStatus.getOohara());
        portStatuses.add(companyStatus.getUehara());
        portStatuses.add(companyStatus.getHatoma());
        portStatuses.add(companyStatus.getHateruma());

        mListAdapter.clear();
        mListAdapter.addAll(portStatuses);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Company company = (Company) getArguments().get(StatusListTabFragment.class.getCanonicalName());
        if (company == null) {
            return;
        }

        PortStatus portStatus = (PortStatus) getListAdapter().getItem(position - 1);
        startStatusDetailActivity(company, portStatus);
    }

    /**
     * 運行詳細に遷移
     */
    private void startStatusDetailActivity(Company company, PortStatus portStatus) {
        Intent intent = new Intent(getActivity(), StatusDetailActivity.class);
        intent.putExtra(Constants.BUNDLE_KEY_COMPANY, company);
        intent.putExtra(Constants.BUNDLE_KEY_PORT_CODE, portStatus.getPortCode());
        intent.putExtra(Constants.BUNDLE_KEY_PORT_NAME, portStatus.getPortName());
        startActivity(intent);
    }
}
