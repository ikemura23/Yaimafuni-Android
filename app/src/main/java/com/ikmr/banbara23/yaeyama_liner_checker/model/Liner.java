
package com.ikmr.banbara23.yaeyama_liner_checker.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Liner implements Parcelable {

    public Company company;
    public Port port;
    public Status status;
    public String text;

    public Liner() {
    }

    protected Liner(Parcel in) {
        company = (Company) in.readValue(Company.class.getClassLoader());
        port = (Port) in.readValue(Port.class.getClassLoader());
        status = (Status) in.readValue(Status.class.getClassLoader());
        text = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(company);
        dest.writeValue(port);
        dest.writeValue(status);
        dest.writeString(text);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Liner> CREATOR = new Parcelable.Creator<Liner>() {
        @Override
        public Liner createFromParcel(Parcel in) {
            return new Liner(in);
        }

        @Override
        public Liner[] newArray(int size) {
            return new Liner[size];
        }
    };

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Port getPort() {
        return port;
    }

    public void setPort(Port port) {
        this.port = port;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Liner{" +
                "company=" + company +
                ", port=" + port +
                ", status=" + status +
                ", text='" + text + '\'' +
                '}';
    }
}
