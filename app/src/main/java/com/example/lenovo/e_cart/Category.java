package com.example.lenovo.e_cart;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.UUID;

public class Category implements Parcelable {

    private UUID mId;
    private String mName;

    Category(){
         mId = UUID.randomUUID();
    }

    Category(String name){
        this();

        mName = name;
    }

    protected Category(Parcel in) {
        mName = in.readString();
    }

    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };

    public UUID getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mName);
    }
}
