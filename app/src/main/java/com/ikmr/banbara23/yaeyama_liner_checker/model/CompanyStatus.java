package com.ikmr.banbara23.yaeyama_liner_checker.model;

import android.os.Parcel;
import android.os.Parcelable;

public class CompanyStatus implements Parcelable {

    private String comment;
    private PortStatus hateruma;
    private PortStatus hatoma;
    private PortStatus kohama;
    private PortStatus kuroshima;
    private PortStatus oohara;
    private PortStatus taketomi;
    private PortStatus uehara;
    private String updateTime;

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return this.comment;
    }

    public void setHateruma(PortStatus hateruma) {
        this.hateruma = hateruma;
    }

    public PortStatus getHateruma() {
        return this.hateruma;
    }

    public void setHatoma(PortStatus hatoma) {
        this.hatoma = hatoma;
    }

    public PortStatus getHatoma() {
        return this.hatoma;
    }

    public void setKohama(PortStatus kohama) {
        this.kohama = kohama;
    }

    public PortStatus getKohama() {
        return this.kohama;
    }

    public void setKuroshima(PortStatus kuroshima) {
        this.kuroshima = kuroshima;
    }

    public PortStatus getKuroshima() {
        return this.kuroshima;
    }

    public void setOohara(PortStatus oohara) {
        this.oohara = oohara;
    }

    public PortStatus getOohara() {
        return this.oohara;
    }

    public void setTaketomi(PortStatus taketomi) {
        this.taketomi = taketomi;
    }

    public PortStatus getTaketomi() {
        return this.taketomi;
    }

    public void setUehara(PortStatus uehara) {
        this.uehara = uehara;
    }

    public PortStatus getUehara() {
        return this.uehara;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateTime() {
        return this.updateTime;
    }

    @Override
    public String toString() {
        return "CompanyStatus{" +
                "comment='" + comment + '\'' +
                ", hateruma=" + hateruma +
                ", hatoma=" + hatoma +
                ", kohama=" + kohama +
                ", kuroshima=" + kuroshima +
                ", oohara=" + oohara +
                ", taketomi=" + taketomi +
                ", uehara=" + uehara +
                ", updateTime='" + updateTime + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.comment);
        dest.writeParcelable(this.hateruma, flags);
        dest.writeParcelable(this.hatoma, flags);
        dest.writeParcelable(this.kohama, flags);
        dest.writeParcelable(this.kuroshima, flags);
        dest.writeParcelable(this.oohara, flags);
        dest.writeParcelable(this.taketomi, flags);
        dest.writeParcelable(this.uehara, flags);
        dest.writeString(this.updateTime);
    }

    public CompanyStatus() {
    }

    protected CompanyStatus(Parcel in) {
        this.comment = in.readString();
        this.hateruma = in.readParcelable(PortStatus.class.getClassLoader());
        this.hatoma = in.readParcelable(PortStatus.class.getClassLoader());
        this.kohama = in.readParcelable(PortStatus.class.getClassLoader());
        this.kuroshima = in.readParcelable(PortStatus.class.getClassLoader());
        this.oohara = in.readParcelable(PortStatus.class.getClassLoader());
        this.taketomi = in.readParcelable(PortStatus.class.getClassLoader());
        this.uehara = in.readParcelable(PortStatus.class.getClassLoader());
        this.updateTime = in.readString();
    }

    public static final Parcelable.Creator<CompanyStatus> CREATOR = new Parcelable.Creator<CompanyStatus>() {
        @Override
        public CompanyStatus createFromParcel(Parcel source) {
            return new CompanyStatus(source);
        }

        @Override
        public CompanyStatus[] newArray(int size) {
            return new CompanyStatus[size];
        }
    };
}
