package com.ikmr.banbara23.yaeyama_liner_checker.front.status.port;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ikmr.banbara23.yaeyama_liner_checker.common.Constants;
import com.ikmr.banbara23.yaeyama_liner_checker.front.status.detail.StatusDetailFragment;
import com.ikmr.banbara23.yaeyama_liner_checker.model.Company;

/**
 * PagerAdapter
 */
public class PortPagerAdapter extends FragmentStatePagerAdapter {
    private final int mTabCount;
    private final String mPortCode;

    public PortPagerAdapter(FragmentManager fm, int tabCount, String portCode) {
        super(fm);
        mTabCount = tabCount;
        mPortCode = portCode;
    }

    @Override
    public Fragment getItem(int position) {
        Company company;
        switch (position) {
            case 0:
                company = Company.ANEI;
                break;
            case 1:
                company = Company.YKF;
                break;
            default:
                company = null;
        }
        Bundle bundle = new Bundle();
        bundle.putString(Constants.BUNDLE_KEY_PORT_CODE, mPortCode);
        bundle.putSerializable(Constants.BUNDLE_KEY_COMPANY, company);
        return StatusDetailFragment.Companion.newInstance(bundle);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return position == 0 ? "安栄観光" : "八重山観光\nフェリー";
    }

    @Override
    public int getCount() {
        return mTabCount;
    }
}
