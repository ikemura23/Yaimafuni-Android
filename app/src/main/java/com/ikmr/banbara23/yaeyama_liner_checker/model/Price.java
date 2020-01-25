package com.ikmr.banbara23.yaeyama_liner_checker.model;

/**
 * 船の料金
 */
public class Price {
    private String adult;
    private String child;
    private String handicapped;

    public String getAdult() {
        return adult;
    }

    public void setAdult(String adult) {
        this.adult = adult;
    }

    public String getChild() {
        return child;
    }

    public void setChild(String child) {
        this.child = child;
    }

    public String getHandicapped() {
        return handicapped;
    }

    public void setHandicapped(String handicapped) {
        this.handicapped = handicapped;
    }
}
