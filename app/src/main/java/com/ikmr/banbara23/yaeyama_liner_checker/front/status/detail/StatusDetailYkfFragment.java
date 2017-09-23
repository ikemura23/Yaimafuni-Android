package com.ikmr.banbara23.yaeyama_liner_checker.front.status.detail;

import com.ikmr.banbara23.yaeyama_liner_checker.core.BaseFragment;

/**
 * ykf詳細のフラグメント
 */
public class StatusDetailYkfFragment extends BaseFragment {

//    private static final String TAG = Const.FireBaseAnalitycsTag.STATUS_DETAIL_DREAM;
//
//    @Bind(R.id.fragment_status_detail_ykf_top_view)
//    StatusDetailTopView mStatusDetailTopView;
//
//    @Bind(R.id.fragment_ykf_status_detail_progressbar)
//    ProgressWheel mProgressBar;
//
//    @Bind(R.id.fragment_status_detail_ykf_distance_time_view)
//    StatusDetailDistanceAndTimeView mStatusDetailDistanceAndTimeView;
//
//    @Bind(R.id.fragment_status_detail_ykf_price_view)
//    StatusDetailPriceView mStatusDetailPriceView;
//
//    @Bind(R.id.fragment_ykf_status_detail_reload_button)
//    Button mReloadButton;
//
//    @Bind(R.id.fragment_time_ykf_table_timetable_view)
//    CardView mFragmentTimeYkfTableTimetableView;
//
//    @Bind(R.id.adView)
//    AdView mAdView;
//
//    /**
//     * リロードボタン押下
//     *
//     * @param view
//     */
//    @OnClick(R.id.fragment_ykf_status_detail_reload_button)
//    void reloadClick(View view) {
//        Activity activity = getActivity();
//        if (activity != null && activity instanceof QueryInterface) {
//            // API通信処理の開始準備の完了
//            ((QueryInterface) activity).startQuery();
//        }
//    }
//
//    /**
//     * 電話する
//     *
//     * @param view
//     */
//    @OnClick(R.id.view_status_detail_tell_layout)
//    void telClick(View view) {
//        startTell();
//        AnalyticsUtils.logSelectEvent(TAG, "tell");
//    }
//
//    /**
//     * webでみる
//     *
//     * @param view
//     */
//    @OnClick(R.id.view_status_detail_web_layout)
//    void webClick(View view) {
//        startWeb();
//        AnalyticsUtils.logSelectEvent(TAG, "web");
//    }
//
//    private CompositeDisposable compositeDisposable = new CompositeDisposable();
//
//    public static StatusDetailYkfFragment NewInstance(YkfLinerDetail ykfLinerDetail) {
//        StatusDetailYkfFragment fragment = new StatusDetailYkfFragment();
//        Bundle bundle = new Bundle();
//        bundle.putParcelable(StatusDetailYkfFragment.class.getName(), ykfLinerDetail);
//        fragment.setArguments(bundle);
//        return fragment;
//    }
//
//    /**
//     * パラメータ取得
//     *
//     * @return
//     */
//    private YkfLinerDetail getParam() {
//        return getArguments().getParcelable(StatusDetailYkfFragment.class.getName());
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        final View view = inflater.inflate(R.layout.fragment_status_detail_ykf, container, false);
//        ButterKnife.bind(this, view);
//        initViews();
//        return view;
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        startQuery();
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        ButterKnife.unbind(this);
//        compositeDisposable.dispose();
//    }
//
//    /**
//     * 外部電話アプリ起動
//     */
//    private void startTell() {
//        String tell = getActivity().getApplicationContext().getString(R.string.tel_ykf);
//        Intent intent = new Intent(
//                Intent.ACTION_VIEW,
//                Uri.parse("tel:" + tell));
//        try {
//            startActivity(intent);
//        } catch (Exception e) {
//            Crashlytics.logException(e);
//        }
//    }
//
//    /**
//     * 外部ブラウザ起動
//     */
//    private void startWeb() {
//        String hpUrl = getActivity().getApplicationContext().getString(R.string.hp_ykf);
//        CustomTabUtil.start(getActivity(), hpUrl);
//    }
//
//    private void initViews() {
//        mStatusDetailDistanceAndTimeView.setDistanceText(null);
//        mStatusDetailDistanceAndTimeView.setTimeText(getTime());
//        mStatusDetailPriceView.setPrice(getPrice());
//        initAdView(mAdView);
//        initTimeTableView();
//    }
//
//    private void initTimeTableView() {
//        mFragmentTimeYkfTableTimetableView.removeAllViews();
//        int viewResourceId = getYkfTimeTableLayoutResourceId();
//        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        inflater.inflate(viewResourceId, mFragmentTimeYkfTableTimetableView);
//    }
//
//    private int getYkfTimeTableLayoutResourceId() {
//
//        switch (getParam().getPort()) {
//            case TAKETOMI:
//                return R.layout.view_time_table_ykf_taketomi;
//            case KOHAMA:
//                return R.layout.view_time_table_ykf_kohama;
//            case KUROSHIMA:
//                return R.layout.view_time_table_ykf_kuroshima;
//            case OOHARA:
//                return R.layout.view_time_table_ykf_oohara;
//            case UEHARA:
//                return R.layout.view_time_table_ykf_uehara;
//            case HATOMA:
//                return R.layout.view_time_table_ykf_hatoma;
//            default:
//                return R.layout.view_time_table_ykf_taketomi;
//        }
//    }
//
//    /**
//     * ステータス取得処理の開始
//     */
//    public void startQuery() {
//        mProgressBar.setVisibility(View.VISIBLE);
//        mStatusDetailTopView.setVisibility(View.GONE);
//
//        // キャッシュ処理
//        CacheManager cacheManager = CacheManager.getInstance();
//        if (cacheManager.isPreferenceCacheDisable() || cacheManager.isExpiryList(Company.YKF)) {
//            // キャッシュが無効なので通信必要
//            startApiQuery();
//            return;
//        }
//        // キャッシュ有効なので不要
//        Result result = cacheManager.getListResultCache(Company.YKF);
//        onResultListQuery(result);
//        finishQuery();
//    }
//
//    private String getTime() {
//        return PortUtil.getYkfTime(getContext(), getParam().getPort());
//    }
//
//    private Price getPrice() {
//        return PortUtil.getYkfPrice(getContext(), getParam().getPort());
//    }
//
//    public Context getContext() {
//        return getActivity().getApplicationContext();
//    }
//
//    /**
//     * 八重山観光フェリーAPIを呼び出す
//     */
//    private void startApiQuery() {
//
//        compositeDisposable.add(
//                StatusListApi.request(Company.YKF)
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
//                            }
//                        })
//        );
//    }
//
//    /**
//     * 一覧を取得した
//     *
//     * @param result
//     */
//    private void onResultListQuery(Result result) {
//        Liner liner = PortUtil.getMyPort(result.getLiners(), getParam().getPort());
//        mStatusDetailTopView.bindStatus(liner);
//        mStatusDetailTopView.setUpdateText(result.getUpdateTime());
//    }
//
//    /**
//     * 取得失敗
//     *
//     * @param e
//     */
//    public void failedQuery(Throwable e) {
//        mReloadButton.setVisibility(View.VISIBLE);
//        Crashlytics.logException(e);
//    }
//
//    /**
//     * 取得完了
//     */
//    public void finishQuery() {
//        mProgressBar.setVisibility(View.GONE);
//        mStatusDetailTopView.setVisibility(View.VISIBLE);
//    }
}
