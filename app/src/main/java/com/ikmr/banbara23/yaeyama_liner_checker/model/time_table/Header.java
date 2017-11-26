package com.ikmr.banbara23.yaeyama_liner_checker.model.time_table;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Header implements Parcelable {

    @SerializedName("left")
    public String left;
    @SerializedName("right")
    public String right;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.left);
        dest.writeString(this.right);
    }

    public Header() {
    }

    protected Header(Parcel in) {
        this.left = in.readString();
        this.right = in.readString();
    }

    public static final Parcelable.Creator<Header> CREATOR = new Parcelable.Creator<Header>() {
        @Override
        public Header createFromParcel(Parcel source) {
            return new Header(source);
        }

        @Override
        public Header[] newArray(int size) {
            return new Header[size];
        }
    };

    public String getLeft() {
        return left;
    }

    @Override
    public String toString() {
        return "Header{" +
                "left='" + left + '\'' +
                ", right='" + right + '\'' +
                '}';
    }

    public void setLeft(String left) {
        this.left = left;
    }

    public String getRight() {
        return right;
    }

    public void setRight(String right) {
        this.right = right;
    }
}