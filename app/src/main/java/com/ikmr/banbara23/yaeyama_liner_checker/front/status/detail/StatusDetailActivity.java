package com.ikmr.banbara23.yaeyama_liner_checker.front.status.detail;

import android.os.Bundle;
import android.view.MenuItem;

import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.common.Constants;
import com.ikmr.banbara23.yaeyama_liner_checker.core.BaseActivity;
import com.ikmr.banbara23.yaeyama_liner_checker.model.Company;

/**
 * ステータス詳細のActivity
 */
public class StatusDetailActivity extends BaseActivity {
    private final String TAG = StatusDetailActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.status_detail_activity);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        setScreenTitle();

        getFragmentManager().beginTransaction()
                .replace(R.id.container, StatusDetailFragment.NewInstance(getIntent().getExtras()))
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
    private void setScreenTitle() {
        Company company = (Company) getIntent().getSerializableExtra(Constants.BUNDLE_KEY_COMPANY);
        if (company == null) {
            return;
        }
        if (getSupportActionBar() == null) {
            return;
        }

        setTitle(company.getName());
    }
}
