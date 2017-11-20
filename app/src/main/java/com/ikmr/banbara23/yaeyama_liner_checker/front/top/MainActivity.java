package com.ikmr.banbara23.yaeyama_liner_checker.front.top;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.databinding.ActivityMainBinding;
import com.ikmr.banbara23.yaeyama_liner_checker.front.setting.SettingActivity;
import com.ikmr.banbara23.yaeyama_liner_checker.front.status.list.StatusListTabActivity;
import com.ikmr.banbara23.yaeyama_liner_checker.front.time_table.TimeTableTabActivity;
import com.ikmr.banbara23.yaeyama_liner_checker.utils.CustomTabUtil;

/**
 * トップ画面
 */
public class MainActivity extends AppCompatActivity implements TopView {

    private final String TAG = MainActivity.class.getSimpleName();
    private TopPresenter presenter;
    TopViewModel viewModel = new TopViewModel();
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        presenter = new TopPresenter(this, viewModel);
        binding.setPresenter(presenter);
        binding.setViewModel(viewModel);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.fetchTopStatus();
        presenter.fetchTopStatus();
    }

    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.top_activity_annei:
                startActivity(new Intent(this, StatusListTabActivity.class));
                break;
            case R.id.top_activity_ykf:
                startActivity(new Intent(this, StatusListTabActivity.class));
                break;
            case R.id.top_activity_dream:
                startActivity(new Intent(this, StatusListTabActivity.class));
                break;
            case R.id.top_activity_timetable:
                startActivity(new Intent(this, TimeTableTabActivity.class));
                break;
            case R.id.top_activity_setting:
                startActivity(new Intent(this, SettingActivity.class));
                break;
            case R.id.activity_top_bubble:
                CustomTabUtil.start(this, "https://tenki.jp/forecast/10/50/9410/47207/3hours.html");
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
        presenter = null;
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void hideProgressBar() {
        binding.activityTopProgressbar.setVisibility(View.GONE);
    }

    @Override
    public void showProgressBar() {
        binding.activityTopProgressbar.setVisibility(View.VISIBLE);
    }
}
