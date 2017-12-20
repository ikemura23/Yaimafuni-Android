package com.ikmr.banbara23.yaeyama_liner_checker.model;

import android.os.Parcel;
import android.os.Parcelable;

public class PortStatus implements Parcelable {

    private String comment;
    private String portCode;
    private String portName;
    private Status status;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPortCode() {
        return portCode;
    }

    public void setPortCode(String portCode) {
        this.portCode = portCode;
    }

    public String getPortName() {
        return portName;
    }

    public void setPortName(String portName) {
        this.portName = portName;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.comment);
        dest.writeString(this.portCode);
        dest.writeString(this.portName);
        dest.writeParcelable(this.status, flags);
    }

    public PortStatus() {
    }

    protected PortStatus(Parcel in) {
        this.comment = in.readString();
        this.portCode = in.readString();
        this.portName = in.readString();
        this.status = in.readParcelable(Status.class.getClassLoader());
    }

    public static final Parcelable.Creator<PortStatus> CREATOR = new Parcelable.Creator<PortStatus>() {
        @Override
        public PortStatus createFromParcel(Parcel source) {
            return new PortStatus(source);
        }

        @Override
        public PortStatus[] newArray(int size) {
            return new PortStatus[size];
        }
    };

    @Override
    public String toString() {
        return "PortStatus{" +
                "comment='" + comment + '\'' +
                ", portCode='" + portCode + '\'' +
                ", portName='" + portName + '\'' +
                ", status=" + status +
                '}';
    }
}
