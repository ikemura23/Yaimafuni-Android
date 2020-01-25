package com.ikmr.banbara23.yaeyama_liner_checker.model.top;

/**
 * Topモデル
 */
public class TopCompanyInfo {
    private TopCompany anei;
    private TopCompany ykf;

    public TopCompany getAnei() {
        return anei;
    }

    public void setAnei(TopCompany anei) {
        this.anei = anei;
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
               ", ykf=" + ykf +
               '}';
    }

    public static class TopCompany {
        private int cancel;
        private int cation;
        private int normal;
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

        public int getNormal() {
            return normal;
        }

        public void setNormal(int normal) {
            this.normal = normal;
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
                   ", normal=" + normal +
                   ", suspend=" + suspend +
                   '}';
        }
    }
}
