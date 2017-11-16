package com.ikmr.banbara23.yaeyama_liner_checker.front.status.detail.anei;

import android.app.Fragment;
import android.os.Bundle;
import android.view.MenuItem;

import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.core.BaseActivity;
import com.ikmr.banbara23.yaeyama_liner_checker.front.status.detail.StatusDetailFragment;
import com.ikmr.banbara23.yaeyama_liner_checker.model.Liner;
import com.ikmr.banbara23.yaeyama_liner_checker.model.PortStatus;
import com.ikmr.banbara23.yaeyama_liner_checker.utils.StringUtils;

/**
 * ステータス詳細のActivity
 */
public class StatusDetailActivity extends BaseActivity {

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
        setContentView(R.layout.status_detail_activity);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setSubtitle(R.string.company_name_annei);
        }
//        ButterKnife.bind(this);

        setTitleString();
        if (savedInstanceState != null) {
            mLiner = (Liner) savedInstanceState.get(PortStatus.class.getCanonicalName());
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

    private Bundle getBundle() {
        return getIntent().getBundleExtra(StatusDetailActivity.class.getName());
    }

    /**
     * フラグメント作成
     */
    private void createFragment() {

        mFragment = StatusDetailFragment.NewInstance(getBundle());
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
        if (getBundle() == null) {
            return;
        }
        if (StringUtils.isEmpty(getBundle().getString("portName"))) {
            setTitle("安栄観光 詳細");
            return;
        }
        setTitle(getBundle().getString("portName"));
    }
}
