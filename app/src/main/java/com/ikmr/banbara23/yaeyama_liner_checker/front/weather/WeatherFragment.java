package com.ikmr.banbara23.yaeyama_liner_checker.front.weather;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.core.BaseFragment;
import com.ikmr.banbara23.yaeyama_liner_checker.databinding.WeatherFragmentBinding;
import com.ikmr.banbara23.yaeyama_liner_checker.model.weather.WeatherViewModel;
import com.ikmr.banbara23.yaeyama_liner_checker.utils.CustomTabUtil;

/**
 * A placeholder fragment containing a simple view.
 */
public class WeatherFragment extends BaseFragment implements WeatherView {

    WeatherPresenter mPresenter;

    public WeatherFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        WeatherFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.weather_fragment, container, false);

        WeatherViewModel today = new WeatherViewModel();
        WeatherViewModel tomorrow = new WeatherViewModel();
        binding.setToday(today);
        binding.setTomorrow(tomorrow);

        mPresenter = new WeatherPresenter(today, tomorrow);
        binding.setPresenter(mPresenter);
        mPresenter.attachView(this);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.detachView();
    }

    @Override
    public void openBrowser() {
        CustomTabUtil.start(getActivity(), getString(R.string.weather_open_url));
    }

    public static WeatherFragment NewInstance() {
        return new WeatherFragment();
    }
}
