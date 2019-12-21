package com.ikmr.banbara23.yaeyama_liner_checker.model.time_table;

import android.os.Parcel;
import android.os.Parcelable;

public class Row implements Parcelable {
    public static final Parcelable.Creator<Row> CREATOR = new Parcelable.Creator<Row>() {
        @Override
        public Row createFromParcel(Parcel source) {
            return new Row(source);
        }

        @Override
        public Row[] newArray(int size) {
            return new Row[size];
        }
    };
    private Item left;
    private Item right;

    public Row() {
    }

    protected Row(Parcel in) {
        this.left = in.readParcelable(Left.class.getClassLoader());
        this.right = in.readParcelable(Right.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.left, flags);
        dest.writeParcelable(this.right, flags);
    }

    public Item getLeft() {
        return left;
    }

    public void setLeft(Item left) {
        this.left = left;
    }

    public Item getRight() {
        return right;
    }

    public void setRight(Item right) {
        this.right = right;
    }
}
