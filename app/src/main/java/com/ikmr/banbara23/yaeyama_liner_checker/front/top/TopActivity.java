package com.ikmr.banbara23.yaeyama_liner_checker.front.top;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.core.BaseActivity;

public class TopActivity extends BaseActivity {
    private final String TAG = TopActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.top_activity);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, TopFragment.newInstance())
                .commit();
    }
}
