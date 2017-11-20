package com.ikmr.banbara23.yaeyama_liner_checker.model;

import com.google.gson.annotations.SerializedName;

/**
 * Topモデル
 */
public class TopCompanyInfo {
    @SerializedName("anei")
    private TopCompany anei;
    @SerializedName("dream")
    private TopCompany dream;
    @SerializedName("ykf")
    private TopCompany ykf;

    public TopCompany getAnei() {
        return anei;
    }

    public void setAnei(TopCompany anei) {
        this.anei = anei;
    }

    public TopCompany getDream() {
        return dream;
    }

    public void setDream(TopCompany dream) {
        this.dream = dream;
    }

    public TopCompany getYkf() {
        return ykf;
    }

    public void setYkf(TopCompany ykf) {
        this.ykf = ykf;
    }

    @Override
    public String toString() {
        return "Top_company{" +
                "anei=" + anei +
                ", dream=" + dream +
                ", ykf=" + ykf +
                '}';
    }

    public static class TopCompany {
        @SerializedName("cancel")
        private int cancel;
        @SerializedName("cation")
        private int cation;
        @SerializedName("nomal")
        private int nomal;
        @SerializedName("suspend")
        private int suspend;

        public int getCancel() {
            return cancel;
        }

        public void setCancel(int cancel) {
            this.cancel = cancel;
        }

        public int getCation() {
            return cation;
        }

        public void setCation(int cation) {
            this.cation = cation;
        }

        public int getNomal() {
            return nomal;
        }

        public void setNomal(int nomal) {
            this.nomal = nomal;
        }

        public int getSuspend() {
            return suspend;
        }

        public void setSuspend(int suspend) {
            this.suspend = suspend;
        }

        @Override
        public String toString() {
            return "TopCompany{" +
                    "cancel=" + cancel +
                    ", cation=" + cation +
                    ", nomal=" + nomal +
                    ", suspend=" + suspend +
                    '}';
        }
    }
}
