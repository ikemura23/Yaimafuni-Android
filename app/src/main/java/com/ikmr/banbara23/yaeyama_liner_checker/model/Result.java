
package com.ikmr.banbara23.yaeyama_liner_checker.model;

import java.util.ArrayList;
import java.util.List;

public class Result {

    private Company mCompany;
    private String mUpdateTime;
    private String mTitle;
    private List<Liner> mLiners;

    public Result() {
    }

    public Company getCompany() {
        return mCompany;
    }

    public void setCompany(Company company) {
        mCompany = company;
    }

    public String getUpdateTime() {
        return mUpdateTime;
    }

    public void setUpdateTime(String updateTime) {
        mUpdateTime = updateTime;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public List<Liner> getLiners() {
        return mLiners;
    }

    public void setLiners(ArrayList<Liner> liners) {
        mLiners = liners;
    }
}
