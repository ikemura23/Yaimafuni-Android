package com.ikmr.banbara23.yaeyama_liner_checker.front.weather;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.databinding.WeatherActivityBinding;

public class WeatherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_activity);
        WeatherActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.weather_activity);
        binding.includeTitleBar.titleBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, WeatherFragment.NewInstance())
                .commit();
    }
}
