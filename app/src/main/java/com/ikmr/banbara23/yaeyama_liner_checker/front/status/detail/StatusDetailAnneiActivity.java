package com.ikmr.banbara23.yaeyama_liner_checker.front.status.detail;

import android.app.Fragment;
import android.os.Bundle;
import android.view.MenuItem;

import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.core.BaseActivity;
import com.ikmr.banbara23.yaeyama_liner_checker.model.Liner;
import com.ikmr.banbara23.yaeyama_liner_checker.utils.StringUtils;

/**
 * ステータス詳細のActivity
 */
public class StatusDetailAnneiActivity extends BaseActivity {

    Liner mLiner;
    Fragment mFragment;
//    private static final String TAG = Const.FireBaseAnalitycsTag.STATUS_DETAIL_ANNEI;
    /**
     * クエリ起動中かどうか
     */
    private boolean mQuerying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_detail);
        mLiner = getIntent().getParcelableExtra(StatusDetailAnneiActivity.class.getName());
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setSubtitle(R.string.company_name_annei);
        }
//        ButterKnife.bind(this);

        setTitleString();
        if (savedInstanceState != null) {
            mLiner = (Liner) savedInstanceState.get(Liner.class.getCanonicalName());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mFragment == null) {
            createFragment();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(Liner.class.getCanonicalName(), mLiner);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mLiner = (Liner) savedInstanceState.get(Liner.class.getCanonicalName());
    }

    /**
     * フラグメント作成
     */
    private void createFragment() {
        mFragment = StatusDetailAnneiFragment.NewInstance(mLiner);
        getFragmentManager().beginTransaction()
                .replace(R.id.container, mFragment)
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * タイトルバーの設定
     */
    private void setTitleString() {
        if (mLiner == null) {
            return;
        }
        if (mLiner.getPort() == null) {
            return;
        }
        if (StringUtils.isEmpty(mLiner.getPort().getPort())) {
            setTitle("運航状況の詳細");
        }

        setTitle(mLiner.getPort().getPort() + "航路");
    }
}
