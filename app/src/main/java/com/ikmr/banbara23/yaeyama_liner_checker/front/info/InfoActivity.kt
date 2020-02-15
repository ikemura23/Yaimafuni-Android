package com.ikmr.banbara23.yaeyama_liner_checker.front.info

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ikmr.banbara23.yaeyama_liner_checker.R

/**
 * アプリ情報Activity
 */
class InfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * アンケートボタン押下
     *
     * @param view
     */
    fun formClick(view: View) {

        val uri = Uri.parse(getString(R.string.form_address))
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }

    /**
     * 評価ボタン押下
     *
     * @param view
     */
    fun playStoreClick(view: View) {

        val googlePlayIntent = Intent(Intent.ACTION_VIEW)
        googlePlayIntent.data = Uri.parse("market://details?id=com.banbara.yaeyama.liner.checker")
        startActivity(googlePlayIntent)
    }

    /**
     * ライセンスクリック
     *
     * @param view
     */
    fun licenseClick(view: View) {
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

    companion object {

        private val TAG = InfoActivity::class.java.simpleName
    }
}
