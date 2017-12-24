package com.ikmr.banbara23.yaeyama_liner_checker.front.status.list;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.core.BaseActivity;
import com.ikmr.banbara23.yaeyama_liner_checker.databinding.ActivityListTabBinding;
import com.ikmr.banbara23.yaeyama_liner_checker.model.Company;

/**
 * 一覧タブActivity
 */
public class StatusListTabActivity extends BaseActivity {

    private static final String TAG = StatusListTabActivity.class.getSimpleName();
    private static final int TAB_FIRST = 0;
    private static final int TAB_SECOND = 1;
    private static final int TAB_THREAD = 2;

    TabLayout tabLayout;

    ViewPager viewPager;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_tab);
        ActivityListTabBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_list_tab);
        binding.includeTitleBar.titleBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tabLayout = binding.activityListTabLayout;
        viewPager = binding.activityListTabViewPager;

        createTab(getCurrentPosition());
    }

    /**
     * 前回開いていたタブ番号を取得
     *
     * @return タブ番号を取得
     */
    private int getCurrentPosition() {
        Company company = (Company) getIntent().getSerializableExtra(StatusListTabActivity.class.getCanonicalName());
        if (company == null) {
            return 0;
        }
        switch (company) {
            case ANEI:
                return TAB_FIRST;
            case YKF:
                return TAB_SECOND;
            case DREAM:
                return TAB_THREAD;
            default:
                return 0;
        }
    }

    /**
     * タブの作成
     *
     * @param position
     */
    private void createTab(int position) {
        if (tabLayout == null) {
            return;
        }
        if (viewPager == null) {
            return;
        }

        tabLayout.removeAllTabs();
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.company_tab_name_annei)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.company_tab_name_ykf)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.company_tab_name_dream)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(position);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
