package com.ikmr.banbara23.yaeyama_liner_checker.front.top;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.common.Constants;
import com.ikmr.banbara23.yaeyama_liner_checker.core.BaseFragment;
import com.ikmr.banbara23.yaeyama_liner_checker.databinding.TopFragmentBinding;
import com.ikmr.banbara23.yaeyama_liner_checker.front.status.list.StatusListTabActivity;
import com.ikmr.banbara23.yaeyama_liner_checker.front.status.port.PortListActivity;
import com.ikmr.banbara23.yaeyama_liner_checker.front.weather.WeatherActivity;
import com.ikmr.banbara23.yaeyama_liner_checker.model.Company;

import org.jetbrains.annotations.NotNull;

public class TopFragment extends BaseFragment implements TopView {
    private static final String TAG = BaseFragment.class.getSimpleName();
    TopFragmentBinding binding;
    TopViewModel topViewModel = new TopViewModel();
    TopPresenter topPresenter;

    public static Fragment newInstance() {
        return new TopFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        topPresenter = new TopPresenter(this, topViewModel);
        topPresenter.attachView(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.top_fragment, container, false);
        binding.setPresenter(topPresenter);
        binding.setViewModel(topViewModel);
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        topPresenter.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        topPresenter.detachView();
        if (binding != null) {
            binding.unbind();
            binding = null;
        }
    }

    /**
     * 天気クリック
     */
    @Override
    public void navigateToWeather() {
        Log.d(TAG, "navigateToWeather");
        startActivity(new Intent(getActivity(), WeatherActivity.class));
    }

    /**
     * 会社別ステータス クリック
     */
    @Override
    public void navigateToCompanyStatusList(@NotNull Company company) {
        Log.d(TAG, "navigateToCompanyStatusList:" + company.getName());
        Intent intent = new Intent(getActivity(), StatusListTabActivity.class);
        intent.putExtra(StatusListTabActivity.class.getCanonicalName(), company);
        startActivity(intent);
    }

    /**
     * 港別ステータス クリック
     */
    @Override
    public void navigateToPortStatusList(@NotNull String portName, @NotNull String portCode) {
        Log.d(TAG, "navigateToPortStatusList:" + portName);
        Intent intent = new Intent(getActivity(), PortListActivity.class);
        intent.putExtra(Constants.BUNDLE_KEY_PORT_NAME, portName);
        intent.putExtra(Constants.BUNDLE_KEY_PORT_CODE, portCode);
        startActivity(intent);
    }
}