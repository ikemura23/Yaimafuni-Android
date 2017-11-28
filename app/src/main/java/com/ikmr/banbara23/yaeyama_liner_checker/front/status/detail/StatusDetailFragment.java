package com.ikmr.banbara23.yaeyama_liner_checker.front.status.detail;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.common.Constants;
import com.ikmr.banbara23.yaeyama_liner_checker.core.BaseFragment;
import com.ikmr.banbara23.yaeyama_liner_checker.databinding.StatusDetailFragmentBinding;
import com.ikmr.banbara23.yaeyama_liner_checker.model.Company;
import com.ikmr.banbara23.yaeyama_liner_checker.utils.CustomTabUtil;

/**
 * 安栄の詳細フラグメント
 */
public class StatusDetailFragment extends BaseFragment implements StatusDetailView {
    private static final String TAG = StatusDetailFragment.class.getSimpleName();
    StatusDetailFragmentBinding binding;
    StatusDetailViewModel viewModel = new StatusDetailViewModel();
    LinerInfoViewModel linerViewModel = new LinerInfoViewModel();
    TimeTableViewModel timeTableViewModel = new TimeTableViewModel();
    StatusDetailPresenter presenter;

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
        presenter.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.onStop();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.status_detail_fragment, container, false);

        presenter = new StatusDetailPresenter(viewModel, linerViewModel, timeTableViewModel, getArgCompany(), getArgPortCode());
        presenter.attachView(this);
        binding.setStatusViewModel(viewModel);
        binding.setLinerInfoViewModel(linerViewModel);
        binding.setTimeTableViewModel(timeTableViewModel);
        binding.setPresenter(presenter);

        return binding.getRoot();
    }

    @Override
    public Context getContext() {
        return this.getActivity();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }

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

    /**
     * 電話アプリを起動
     *
     * @param tel
     */
    @Override
    public void openTell(String tel) {
        Intent intent = new Intent(
                Intent.ACTION_VIEW,
                Uri.parse("tel:" + tel));
        try {
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 外部ブラウザを起動
     *
     * @param url
     */
    @Override
    public void openBrowser(String url) {
        CustomTabUtil.start(getActivity(), url);
    }
}
