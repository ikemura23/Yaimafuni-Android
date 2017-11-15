package com.ikmr.banbara23.yaeyama_liner_checker.front.status.detail;

import android.os.Bundle;
import android.view.MenuItem;

import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.core.BaseActivity;
import com.ikmr.banbara23.yaeyama_liner_checker.model.Liner;
import com.ikmr.banbara23.yaeyama_liner_checker.model.PortStatus;
import com.ikmr.banbara23.yaeyama_liner_checker.utils.StringUtils;

/**
 * ステータス詳細のActivity
 */
public class StatusDetailActivity extends BaseActivity {

    Liner mLiner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.status_detail_activity);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setSubtitle(R.string.company_name_annei);
        }

        setTitleString();
        if (savedInstanceState != null) {
            mLiner = (Liner) savedInstanceState.get(PortStatus.class.getCanonicalName());
        }

        getFragmentManager().beginTransaction()
                .replace(R.id.container, StatusDetailFragment.NewInstance(getParamPortStatus()))
                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
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

    private PortStatus getParamPortStatus() {
        return getIntent().getParcelableExtra(StatusDetailActivity.class.getName());
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
        PortStatus portStatus = getParamPortStatus();
        if (getParamPortStatus() == null) {
            return;
        }
        if (StringUtils.isEmpty(portStatus.getPortName())) {
            setTitle("安栄観光 詳細");
            return;
        }
        setTitle(portStatus.getPortName());
    }
}
