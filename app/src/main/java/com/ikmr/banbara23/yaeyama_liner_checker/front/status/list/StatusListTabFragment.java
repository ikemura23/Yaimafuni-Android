
package com.ikmr.banbara23.yaeyama_liner_checker.front.status.list;

import android.app.Activity;
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
import com.ikmr.banbara23.yaeyama_liner_checker.core.BaseListFragment;
import com.ikmr.banbara23.yaeyama_liner_checker.front.status.detail.anei.StatusDetailAnneiActivity;
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
//    private AdView mAdView;

//    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    // ButterKnife BindString --------------------------------------------
//    @BindString(R.string.url_annei_list)
//    String URL_ANNEI_LIST;
//
//    @Bind(R.id.fragment_status_list_progressbar)
//    ProgressWheel mProgressWheel;
//
//    @Bind(R.id.fragment_status_list_empty_button)
//    Button emptyButton;

    /**
     * リロードタップ
     *
     * @param view リロードボタン
     */
//    @OnClick(R.id.fragment_status_list_empty_button)
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
//        mAdView = (AdView) view.findViewById(R.id.adView);
//        ButterKnife.bind(this, view);
        initViews();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        startQuery();
//        if (mAdView != null) {
//            mAdView.resume();
//        }
    }

    @Override
    public void onPause() {
        super.onPause();
//        if (mAdView != null) {
//            mAdView.pause();
//        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        ButterKnife.unbind(this);
//        compositeDisposable.dispose();
//        if (mAdView != null) {
//            mAdView.destroy();
//        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getListView().addHeaderView(mHeaderView, null, false);
        Activity activity = getActivity();
//        if (activity != null && activity instanceof QueryInterface) {
//            // API通信処理の開始準備の完了
//            ((QueryInterface) activity).startQuery();
//        }
    }

    private void initViews() {
        mHeaderView = View.inflate(getActivity(), R.layout.fragment_status_list_header_view, null);
        mTitleText = (TextView) mHeaderView.findViewById(R.id.fragment_status_list_toolbar_title_text);
        mUpdateText = (TextView) mHeaderView.findViewById(R.id.fragment_status_list_toolbar_update_text);
        mListAdapter = new StatusListAdapter(getContext());
//        final AdRequest adRequest = new AdRequest.Builder().build();
//        getActivity().runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                mAdView.loadAd(adRequest);
//            }
//        });
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
//        mProgressWheel.setVisibility(View.VISIBLE);
        mHeaderView.setVisibility(View.GONE);
        mListAdapter.clear();
        setListAdapter(mListAdapter);
//        emptyButton.setVisibility(View.GONE);
        // キャッシュ処理
//        CacheManager cacheManager = CacheManager.getInstance();
//        if (cacheManager.isPreferenceCacheDisable() || cacheManager.isExpiryList(getCompany())) {
//            // キャッシュが無効なので通信必要
//            startListQuery();
//            // startDebugListQuery();
//            return;
//        }
        // キャッシュ有効なので不要
//        Result result = cacheManager.getListResultCache(getCompany());
//        onResultListQuery(result);
//        finishQuery();
        String table;
        switch (getCompany()) {
            case ANNEI:
                table = "anei";
                break;
            case YKF:
                table = "ykf";
                break;
            case DREAM:
                table = "dream";
                break;
            default:
                table = "anei";
        }
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(table);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
//                Map<String, JSONObject> map = (Map<String, JSONObject>) dataSnapshot.getValue();
//                String jsonString = new Gson().toJson(map);
////                Log.d(TAG, "Value is: " + map.toString());
//                CompanyStatus companyStatus = new Gson().fromJson(jsonString, CompanyStatus.class);
                CompanyStatus companyStatus = dataSnapshot.getValue(CompanyStatus.class);
                onResultListQuery(companyStatus);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    /**
     * 一覧の取得処理開始
     */
    private void startListQuery() {
//        Company company = getCompany();
//        compositeDisposable.add(
//                StatusListApi.request(company)
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribeOn(Schedulers.newThread())
//                        .subscribeWith(new DisposableObserver<Result>() {
//                            @Override
//                            public void onComplete() {
//                                // 完了
//                                finishQuery();
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//                                // 失敗
//                                failedQuery(e);
//                            }
//
//                            @Override
//                            public void onNext(Result result) {
//                                // 値うけとる
//                                onResultListQuery(result);
//                                saveResultToCache(result);
//                            }
//                        })
//        );
    }

    /**
     * 通信した結果をキャッシュに保存
     *
     * @param result 通信値
     */
//    private void saveResultToCache(Result result) {
//        CacheManager.getInstance().saveNowListTimeStamp(getCompany());
//        CacheManager.getInstance().putResult(getCompany(), result);
//    }

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

    public void finishQuery() {
        mHeaderView.setVisibility(View.VISIBLE);
//        mProgressWheel.setVisibility(View.GONE);
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

    public void failedQuery(Throwable e) {
        mHeaderView.setVisibility(View.GONE);
//        mProgressWheel.setVisibility(View.GONE);
//        emptyButton.setVisibility(View.VISIBLE);
//        Crashlytics.logException(e);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Company company = (Company) getArguments().get(StatusListTabFragment.class.getCanonicalName());
        if (company == null) {
            return;
        }

        PortStatus portStatus = (PortStatus) getListAdapter().getItem(position - 1);
//        liner.setCompany(company);
        switch (company) {
            case ANNEI:
                startStatusDetailActivity(portStatus);
                break;
            case YKF:
                startStatusDetailYkfActivity(portStatus);
                break;
            case DREAM:
                startStatusDetailDreamActivity(portStatus);
                break;
            default:
                break;
        }
//        AnalyticsUtils.logSelectEvent(TAG, "list_item");
    }

    /**
     * 安栄の詳細画面に遷移
     */
    private void startStatusDetailActivity(PortStatus portStatus) {
        Intent intent = new Intent(getActivity(), StatusDetailAnneiActivity.class);
        intent.putExtra(StatusDetailAnneiActivity.class.getName(), portStatus);
        startActivity(intent);
    }

    /**
     * 八重山観光フェリーの詳細に遷移
     *
     * @param liner
     */
    private void startStatusDetailYkfActivity(PortStatus portStatus) {
//        YkfLinerDetail ykfLinerDetail = new YkfLinerDetail();
//        ykfLinerDetail.setLiner(liner);
//        ykfLinerDetail.setPort(liner.getPort());
//
//        Intent intent = new Intent(getActivity(), StatusDetailYkfActivity.class);
//        intent.putExtra(StatusDetailYkfActivity.class.getName(), ykfLinerDetail);
//        startActivity(intent);
    }

    /**
     * ドリーム観光の詳細に遷移
     *
     * @param liner
     */
    private void startStatusDetailDreamActivity(PortStatus portStatus) {
//        YkfLinerDetail ykfLinerDetail = new YkfLinerDetail();
//        ykfLinerDetail.setLiner(liner);
//        ykfLinerDetail.setPort(liner.getPort());
//
//        Intent intent = new Intent(getActivity(), StatusDetailDreamActivity.class);
//        intent.putExtra(StatusDetailDreamActivity.class.getName(), ykfLinerDetail);
//        startActivity(intent);
    }

//    public void resetTimeStamp() {
//        CacheManager.getInstance().resetTimeStamp(getCompany());
//    }
}
