package com.example.curs1academy;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class UserData implements /*Serializable,*/ Parcelable {
    String nume;
    String parola;

    public UserData(String nume, String parola) {
        this.nume = nume;
        this.parola = parola;
    }

    protected UserData(Parcel in) {
        nume = in.readString();
        parola = in.readString();
    }

    public static final Creator<UserData> CREATOR = new Creator<UserData>() {
        @Override
        public UserData createFromParcel(Parcel in) {
            return new UserData(in);
        }

        @Override
        public UserData[] newArray(int size) {
            return new UserData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nume);
        dest.writeString(parola);
    }
}
