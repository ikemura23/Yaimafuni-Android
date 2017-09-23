
package com.ikmr.banbara23.yaeyama_liner_checker.front.info;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.ikmr.banbara23.yaeyama_liner_checker.R;

/**
 * アプリ情報Activity
 */
public class InfoActivity extends AppCompatActivity {

    private static final String TAG = InfoActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
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

    // /**
    // * メール問い合わせボタン押下
    // *
    // * @param view
    // */
    // public void mailClick(View view) {
    //
    // Intent intent = new Intent();
    // intent.setAction(Intent.ACTION_SENDTO);
    // String mailAddress = getString(R.string.mail_address);
    // intent.setData(Uri.parse("mailto:" + mailAddress));
    //
    // String subject = getString(R.string.app_name) +
    // getString(R.string.EXTRA_SUBJECT);
    // intent.putExtra(Intent.EXTRA_SUBJECT, subject);
    //
    // try {
    // startActivity(intent);
    // } catch (Exception e) {
    // Toast.makeText(this, "メール起動でエラーが発生しました", Toast.LENGTH_SHORT).show();
    // }
    // }

    /**
     * アンケートボタン押下
     *
     * @param view
     */
    public void formClick(View view) {

        Uri uri = Uri.parse(getString(R.string.form_address));
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        try {
            startActivity(intent);
        } catch (Exception e) {
//            Crashlytics.logException(e);
        }
    }

    /**
     * 評価ボタン押下
     *
     * @param view
     */
    public void playStoreClick(View view) {

        Intent googlePlayIntent = new Intent(Intent.ACTION_VIEW);
        googlePlayIntent.setData(Uri.parse("market://details?id=com.banbara.yaeyama.liner.checker"));
        try {
            startActivity(googlePlayIntent);
        } catch (Exception e) {
//            Crashlytics.logException(e);
        }
//        AnalyticsUtils.logSelectEvent(TAG, "playStore");
    }

    /**
     * ライセンスクリック
     *
     * @param view
     */
    public void licenseClick(View view) {
//        final Notices notices = new Notices();
//        notices.addNotice(new Notice("Material-ish Progress", "https://github.com/pnikosis/materialish-progress", "Copyright 2014 Nico Hormazábal", new ApacheSoftwareLicense20()));
//        notices.addNotice(new Notice("Butter Knife", "https://github.com/JakeWharton/butterknife", "Copyright 2013 Jake Wharton", new ApacheSoftwareLicense20()));
//        notices.addNotice(new Notice("RxJava", "https://github.com/ReactiveX/RxJava", "Copyright 2013 Netflix, Inc.", new ApacheSoftwareLicense20()));
//        notices.addNotice(new Notice("RxAndroid", "https://github.com/ReactiveX/RxAndroid", "Copyright 2015 The RxAndroid authors", new ApacheSoftwareLicense20()));
//        notices.addNotice(new Notice("Material Ripple Layout", "https://github.com/balysv/material-ripple", "Copyright 2015 Balys Valentukevicius", new ApacheSoftwareLicense20()));
//        notices.addNotice(new Notice("Timber", "https://github.com/JakeWharton/timber", "Copyright 2013 Jake Wharton", new ApacheSoftwareLicense20()));
//        notices.addNotice(new Notice("BubbleLayout", "https://github.com/MasayukiSuda/BubbleLayout", "Copyright 2016 MasayukiSuda", new MITLicense()));
//        new LicensesDialog.Builder(this)
//                .setNotices(notices)
//                .setIncludeOwnLicense(true)
//                .build()
//                .show();
//        AnalyticsUtils.logSelectEvent(TAG, "license");
    }
}
