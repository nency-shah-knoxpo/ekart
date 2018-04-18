package com.example.lenovo.e_cart;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.UUID;

public class Product implements Parcelable {

    protected Product(Parcel in) {
        mProductName = in.readString();
        mProductDesc = in.readString();
        mProductAmount = in.readInt();
        mIsOutOfStock = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mProductName);
        dest.writeString(mProductDesc);
        dest.writeInt(mProductAmount);
        dest.writeByte((byte) (mIsOutOfStock ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public UUID getProductID() {
        return mProductID;
    }

    private UUID mProductID;
    private String mProductName, mProductDesc;
    private int mProductAmount;
    private boolean mIsOutOfStock = false;

    public Product() {
        mProductID = UUID.randomUUID();

    }


    public void setProductName(String productName) {
        mProductName = productName;
    }


    public String getProductName() {
        return mProductName;
    }

    public void setProductDesc(String productDesc) {
        mProductDesc = productDesc;
    }

    public String getProductDesc() {
        return mProductDesc;
    }

    public void setProductAmount(int productAmount) {
        mProductAmount = productAmount;
    }

    public int getProductAmount() {
        return mProductAmount;
    }


    public boolean isOutOfStock() {
        return mIsOutOfStock;
    }

    public void setOutOfStock(boolean outOfStock) {
        mIsOutOfStock = outOfStock;
    }
}
