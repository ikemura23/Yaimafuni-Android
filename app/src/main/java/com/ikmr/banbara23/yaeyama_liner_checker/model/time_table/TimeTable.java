package com.ikmr.banbara23.yaeyama_liner_checker.model.time_table;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TimeTable implements Parcelable {

    @SerializedName("header")
    public Header header;
    @SerializedName("row")
    public List<Row> row;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.header, flags);
        dest.writeList(this.row);
    }

    public TimeTable() {
    }

    protected TimeTable(Parcel in) {
        this.header = in.readParcelable(Header.class.getClassLoader());
        this.row = new ArrayList<Row>();
        in.readList(this.row, Row.class.getClassLoader());
    }

    public static final Parcelable.Creator<TimeTable> CREATOR = new Parcelable.Creator<TimeTable>() {
        @Override
        public TimeTable createFromParcel(Parcel source) {
            return new TimeTable(source);
        }

        @Override
        public TimeTable[] newArray(int size) {
            return new TimeTable[size];
        }
    };

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public List<Row> getRow() {
        return row;
    }

    public void setRow(List<Row> row) {
        this.row = row;
    }

    @Override
    public String toString() {
        return "TimeTable{" +
                "header=" + header +
                ", row=" + row +
                '}';
    }
}
