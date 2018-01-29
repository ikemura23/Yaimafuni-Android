
package com.ikmr.banbara23.yaeyama_liner_checker.front.status.list;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.api.ApiClient;
import com.ikmr.banbara23.yaeyama_liner_checker.common.Constants;
import com.ikmr.banbara23.yaeyama_liner_checker.core.BaseListFragment;
import com.ikmr.banbara23.yaeyama_liner_checker.front.status.detail.StatusDetailActivity;
import com.ikmr.banbara23.yaeyama_liner_checker.model.Company;
import com.ikmr.banbara23.yaeyama_liner_checker.model.CompanyStatus;
import com.ikmr.banbara23.yaeyama_liner_checker.model.PortStatus;
import com.ikmr.banbara23.yaeyama_liner_checker.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subscribers.ResourceSubscriber;

/**
 * 一覧タブListFragment
 */
public class StatusListTabFragment extends BaseListFragment {

    private static final String TAG = StatusListTabFragment.class.getSimpleName();
    StatusListAdapter mListAdapter;
    TextView mTitleText;
    TextView mUpdateText;
    View mHeaderView;
    private CompositeDisposable mDisposable = new CompositeDisposable();
    private ResourceSubscriber<CompanyStatus> resourceSubscriber;

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
        mDisposable.clear();
        if (resourceSubscriber != null) {
            resourceSubscriber.dispose();
        }
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
        resourceSubscriber =
                new ApiClient()
                        .getCompanyStatus(getCompany().getCode())
                        .subscribeWith(new ResourceSubscriber<CompanyStatus>() {

                            @Override
                            public void onNext(CompanyStatus companyStatus) {
                                onResultListQuery(companyStatus);
                            }

                            @Override
                            public void onError(Throwable t) {

                            }

                            @Override
                            public void onComplete() {

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
        portStatuses.add(companyStatus.getTaketomi());
        portStatuses.add(companyStatus.getKohama());
        portStatuses.add(companyStatus.getKuroshima());
        portStatuses.add(companyStatus.getOohara());
        if (getCompany() != Company.DREAM) {
            portStatuses.add(companyStatus.getUehara());
            portStatuses.add(companyStatus.getHatoma());
        } else {
            portStatuses.add(companyStatus.getUehara());
        }
        if (companyStatus.getHateruma() != null) {
            portStatuses.add(companyStatus.getHateruma());
        }
        // 広告
        PortStatus ad = new PortStatus();
        ad.setPortCode("ad");
        portStatuses.add(ad);

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
