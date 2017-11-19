
package com.ikmr.banbara23.yaeyama_liner_checker.front.status.detail;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.common.Constants;
import com.ikmr.banbara23.yaeyama_liner_checker.core.BaseFragment;
import com.ikmr.banbara23.yaeyama_liner_checker.databinding.StatusDetailFragmentBinding;
import com.ikmr.banbara23.yaeyama_liner_checker.model.Company;
import com.ikmr.banbara23.yaeyama_liner_checker.model.PortStatus;

/**
 * 安栄の詳細フラグメント
 */
public class StatusDetailFragment extends BaseFragment implements StatusDetailView {
    private static final String TAG = StatusDetailFragment.class.getSimpleName();
    StatusDetailFragmentBinding binding;
    StatusDetailViewModel viewModel = new StatusDetailViewModel();
    StatusDetailPresenter presenter;
//    private static final String TAG = Constants.FireBaseAnalitycsTag.STATUS_DETAIL_ANNEI;

    //    // ButterKnife Bind View --------------------------------------------
//    @Bind(R.id.fragment_status_detail_progressbar)
//    ProgressWheel mProgressBar;
//
//    @Bind(R.id.fragment_status_detail_reload_button)
//    Button mFragmentStatusDetailErrorButton;
//
//    @Bind(R.id.fragment_status_detail_top_view)
//    StatusDetailTopView mStatusDetailTopView;
//
//    @Bind(R.id.fragment_time_annei_table_timetable_view)
//    CardView mTimeTableLayout;
//
//    @Bind(R.id.fragment_status_detail_distance_time_view)
//    StatusDetailDistanceAndTimeView mDistanceTimeView;
//
//    @Bind(R.id.fragment_status_detail_price_view)
//    StatusDetailPriceHandicappedView mPriceView;
//
//    @Bind(R.id.adView)
//    AdView mAdView;
//
//    // ButterKnife OnClick --------------------------------------------
//
//    /**
//     * エラー時の再読み込みボタン押下
//     *
//     * @param view
//     */
//    @OnClick(R.id.fragment_status_detail_reload_button)
//    void reloadButtonClick(View view) {
//        Activity activity = getActivity();
//        if (activity != null && activity instanceof QueryInterface) {
//            // API通信処理の開始準備の完了
//            ((QueryInterface) activity).startQuery();
//        }
//        AnalyticsUtils.logSelectEvent(TAG, "reload");
//    }
//
//    /**
//     * サイトを観る
//     *
//     * @param view
//     */
//    @OnClick(R.id.view_status_detail_web_layout)
//    void telClick(View view) {
//        startWeb();
//        AnalyticsUtils.logSelectEvent(TAG, "web");
//    }
//
//    /**
//     * 電話をする
//     *
//     * @param view
//     */
//    @OnClick(R.id.view_status_detail_tell_layout)
//    void webClick(View view) {
//        startTel();
//        AnalyticsUtils.logSelectEvent(TAG, "tell");
//    }
//
//    // ButterKnife BindString --------------------------------------------
//    // 一覧URL
//    @BindString(R.string.url_annei_list)
//    String ANNEI_LIST_URL;
//
//    // 電話番号
//    @BindString(R.string.tel_annei)
//    String TEL_ANNEI;
//
//    // プライベート変数 --------------------------------------------
//    private boolean listQuerying = false;
//    private boolean detailQuerying = false;
//
//    private CompositeDisposable compositeDisposable = new CompositeDisposable();
//

    /**
     * New Instance
     *
     * @param bundle
     * @return
     */
    public static StatusDetailFragment NewInstance(Bundle bundle) {
        StatusDetailFragment fragment = new StatusDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.loadPortDetail();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.status_detail_fragment, container, false);

        presenter = new StatusDetailPresenter(viewModel, getArgCompany(), getArgPortCode());
        presenter.attachView(this);
        binding.setStatusViewModel(viewModel);

        return binding.getRoot();
    }

    @Override
    public Context getContext() {
        return this.getActivity();
    }

    //
//    private void initTimeTableView() {
//        mTimeTableLayout.removeAllViews();
//        int viewResourceId = getAnneiTimeTableLayoutResourceId();
//        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        inflater.inflate(viewResourceId, mTimeTableLayout);
//    }
//
//    private int getAnneiTimeTableLayoutResourceId() {
//
//        switch (getPort()) {
//            case TAKETOMI:
//                return R.layout.view_time_table_annei_taketomi;
//            case KOHAMA:
//                return R.layout.view_time_table_annei_kohama;
//            case KUROSHIMA:
//                return R.layout.view_time_table_annei_kuroshima;
//            case OOHARA:
//                return R.layout.view_time_table_annei_oohara;
//            case UEHARA:
//                return R.layout.view_time_table_annei_uehara;
//            case HATOMA:
//                return R.layout.view_time_table_annei_hatoma;
//            case HATERUMA:
//                return R.layout.view_time_table_annei_hateruma;
//            default:
//                return R.layout.view_time_table_annei_taketomi;
//        }
//    }
//
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }
//

    /**
     * パラメータ取得 会社
     *
     * @return
     */
    private Company getArgCompany() {
        return (Company) getArguments().getSerializable(Constants.BUNDLE_KEY_COMPANY);
    }

    /**
     * パラメータ取得 港コード
     *
     * @return
     */
    private String getArgPortCode() {
        return getArguments().getString(Constants.BUNDLE_KEY_PORT_CODE);
    }

    //
//    private Port getPort() {
//        return getParam().getPort();
//    }
//
//    /**
//     * 取得の開始
//     */
    public void startQuery() {

        String path = getTablePath();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(path);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
//                Map<String, JSONObject> map = (Map<String, JSONObject>) dataSnapshot.getValue();
//                String jsonString = new Gson().toJson(map);
////                Log.d(TAG, "Value is: " + map.toString());
//                CompanyStatus companyStatus = new Gson().fromJson(jsonString, CompanyStatus.class);
                PortStatus portStatus = dataSnapshot.getValue(PortStatus.class);
                setViewModel(portStatus);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

//        mStatusDetailTopView.setVisibility(View.GONE);
//        mFragmentStatusDetailErrorButton.setVisibility(View.GONE);
//        mProgressBar.setVisibility(View.VISIBLE);
//
//        if (getActivity() != null && getActivity() instanceof FragmentApiQueryInterface) {
//            // API通信処理の開始準備の完了
//            ((FragmentApiQueryInterface) getActivity()).startQuery();
//        }
//
//        // 処理フラグをON
//        listQuerying = true;
//        detailQuerying = true;
//
//        startDetailQuery();
//        startListQuery();
    }

    private void setViewModel(PortStatus portStatus) {
        viewModel.portStatus.set(portStatus);
        Log.d(TAG, portStatus.toString());
    }

    public String getTablePath() {
        String companyName = getArgCompany().getCode();
        String portCode = getArgPortCode();
        return companyName + "/" + portCode;
    }

//
//    /**
//     * 取得処理の手前でキャッシュ取得か、通信するかの判定
//     */
//    private void startDetailQuery() {
//        // キャッシュ処理
//        CacheManager cacheManager = CacheManager.getInstance();
//        if (cacheManager.isPreferenceCacheDisable() || cacheManager.isExpiryDetailAnnei(getPort())) {
//            // キャッシュが無効なので通信必要
//            startAnneiDetailQuery();
//            return;
//        }
//        // キャッシュ有効なので不要
//        String comment = cacheManager.getDetailAnneiResultCache();
//        onResultDetailQuery(comment);
//        detailQuerying = false;
//        finishQuery();
//    }
//
//    /**
//     * 安栄のTOPの一覧を取得
//     */
//    private void startAnneiDetailQuery() {
//        String url = PortUtil.getAnneiDetailUrl(getActivity().getApplicationContext(), getPort());
//        AnneiStatusDetailApi.request(url)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.newThread())
//                .subscribe(new DisposableObserver<String>() {
//
//                    @Override
//                    public void onError(Throwable e) {
//                        detailQuerying = false;
//                        Crashlytics.logException(e);
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        // 完了
//                        detailQuerying = false;
//                        finishQuery();
//                    }
//
//                    @Override
//                    public void onNext(String s) {
//                        // 値うけとる
//                        onResultDetailQuery(s);
//                        saveResultDetailToCache(s);
//                    }
//                });
//    }
//
//    /**
//     * 通信で取得した詳細をキャッシュに保存
//     *
//     * @param value 詳細
//     */
//    private void saveResultDetailToCache(String value) {
//        CacheManager.getInstance().saveNowDetailAnneiTimeStamp(getPort());
//        CacheManager.getInstance().putDetail(getPort(), value);
//    }
//
//    /**
//     * 安栄の港の詳細を取得
//     */
//    private void startListQuery() {
//        // キャッシュ処理
//        CacheManager cacheManager = CacheManager.getInstance();
//        if (cacheManager.isExpiryList(Company.ANEI)) {
//            // キャッシュが無効なので通信必要
//            startAnneiListQuery();
//            return;
//        }
//        // キャッシュ有効なので不要
//        Result result = cacheManager.getListResultCache(Company.ANEI);
//        onResultListQuery(result);
//        listQuerying = false;
//        detailQuerying = false;
//        finishQuery();
//    }
//
//    /**
//     * 通信で安栄の一覧を取得
//     */
//    private void startAnneiListQuery() {
//        compositeDisposable.add(
//                StatusListApi.request(Company.ANEI)
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribeOn(Schedulers.newThread())
//                        .subscribeWith(new DisposableObserver<Result>() {
//                            @Override
//                            public void onComplete() {
//                                // 完了
//                                listQuerying = false;
//                                finishQuery();
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//                                // 失敗
//                                listQuerying = false;
//                                failedQuery(e);
//                            }
//
//                            @Override
//                            public void onNext(Result result) {
//                                // 値うけとる
//                                onResultListQuery(result);
//                                saveResultListToCache(result);
//                            }
//                        })
//        );
//    }
//
//    /**
//     * 通信した結果をキャッシュに保存
//     *
//     * @param result 通信値
//     */
//    private void saveResultListToCache(Result result) {
//        CacheManager.getInstance().saveNowListTimeStamp(Company.ANEI);
//        CacheManager.getInstance().putResult(Company.ANEI, result);
//    }
//
//    /**
//     * 一覧を取得した
//     *
//     * @param result
//     */
//    private void onResultListQuery(Result result) {
//        if (result == null || result.getLiners().isEmpty() || result.getLiners().size() == 0) {
//            failedQuery(new Exception("AnneiDetailApiQueryError : Annei status list api result a Null or Empty"));
//            return;
//        }
//
//        Liner liner = PortUtil.getMyPort(result.getLiners(), getPort());
//
//        mStatusDetailTopView.bindStatus(liner);
//        mStatusDetailTopView.setUpdateText(result.getUpdateTime());
//
//        mDistanceTimeView.setDistanceText(getAnneiDistance());
//        mDistanceTimeView.setTimeText(getAnneiTime());
//
//        mPriceView.setPrice(getPrice());
//    }
//
//    /**
//     * 詳細を取得した
//     *
//     * @param comment
//     */
//    private void onResultDetailQuery(String comment) {
//        if (comment == null) {
//            mStatusDetailTopView.setVisibility(View.GONE);
//            return;
//        }
//        mStatusDetailTopView.setCommentText(comment);
//    }
//
//    /**
//     * 走行距離
//     *
//     * @return 距離
//     */
//    private String getAnneiDistance() {
//        return PortUtil.getAnneiDistance(getContext(), getPort());
//    }
//
//    /**
//     * 走行時間
//     *
//     * @return 走行時間
//     */
//    private String getAnneiTime() {
//        return PortUtil.getAnneiTime(getContext(), getPort());
//    }
//
//    /**
//     * 料金・大人
//     *
//     * @return 料金・大人
//     */
//    public Price getPrice() {
//        return PortUtil.getAnneiPrice(getContext(), getPort());
//    }
//
//    /**
//     * 取得失敗
//     *
//     * @param e
//     */
//    public void failedQuery(Throwable e) {
//        Crashlytics.logException(e);
//        mProgressBar.setVisibility(View.GONE);
//        mFragmentStatusDetailErrorButton.setVisibility(View.VISIBLE);
//    }
//
//    /**
//     * 取得完了
//     */
//    public void finishQuery() {
//        if (listQuerying || detailQuerying) {
//            return;
//        }
//        mProgressBar.setVisibility(View.GONE);
//        mStatusDetailTopView.setVisibility(View.VISIBLE);
//        if (getActivity() != null && getActivity() instanceof FragmentApiQueryInterface) {
//            // API通信処理の開始準備の完了
//            ((FragmentApiQueryInterface) getActivity()).finishQuery();
//        }
//    }
//
//    /**
//     * 外部電話アプリを起動
//     */
//    private void startTel() {
//        try {
//            Intent intent = new Intent(
//                    Intent.ACTION_VIEW,
//                    Uri.parse("tel:" + TEL_ANNEI));
//
//            startActivity(intent);
//        } catch (Exception e) {
//            Crashlytics.logException(e);
//        }
//    }
//
//    /**
//     * 外部ブラウザを起動
//     */
//    private void startWeb() {
//        String urlString = PortUtil.getAnneiDetailUrl(getContext(), getPort());
//        CustomTabUtil.start(getActivity(), urlString);
//    }
}
