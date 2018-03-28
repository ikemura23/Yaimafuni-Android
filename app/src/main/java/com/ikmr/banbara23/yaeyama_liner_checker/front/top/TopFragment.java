package com.ikmr.banbara23.yaeyama_liner_checker.front.top;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.core.BaseFragment;
import com.ikmr.banbara23.yaeyama_liner_checker.databinding.TopRootBinding;

public class TopFragment extends BaseFragment implements TopView {
    TopRootBinding binding;
    TopViewModel topViewModel;
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
        binding = DataBindingUtil.inflate(inflater, R.layout.top_root, container, false);

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
    }

    @Override
    public void hideProgressBar() {

    }

    @Override
    public void showProgressBar() {

    }
}
