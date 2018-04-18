package com.example.lenovo.e_cart;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.UUID;

public class Category {

    private UUID mId;
    private String mName;


    Category(){
         mId = UUID.randomUUID();
    }

    Category(String name){
        this();

        mName = name;
    }

    public UUID getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}
