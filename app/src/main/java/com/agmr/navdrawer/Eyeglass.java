package com.agmr.navdrawer;

import com.google.firebase.database.Exclude;

public class Eyeglass {
    private String mBrand, mModel, mColor, mGender, mImageUrl, mKey;
    private float mPrice;

    public Eyeglass() {

    }

    public Eyeglass(String brand, String mModel, String mColor, String mGender, float mPrice) {
        mBrand = brand;
        this.mModel = mModel;
        this.mColor = mColor;
        this.mGender = mGender;
        this.mPrice = mPrice;
    }

    public Eyeglass(String brand, String mModel, String mColor, String mGender, float mPrice, String mImageUrl) {
        mBrand = brand;
        this.mModel = mModel;
        this.mColor = mColor;
        this.mGender = mGender;
        this.mPrice = mPrice;
        this.mImageUrl = mImageUrl;

    }

    public String getmGender() {
        return mGender;
    }

    public void setmGender(String mGender) {
        this.mGender = mGender;
    }

    public String getmBrand() {
        return mBrand;
    }

    public void setmBrand(String mBrand) {
        this.mBrand = mBrand;
    }

    public String getmModel() {
        return mModel;
    }

    public void setmModel(String mModel) {
        this.mModel = mModel;
    }

    public String getmColor() {
        return mColor;
    }

    public void setmColor(String mColor) {
        this.mColor = mColor;
    }

    public float getmPrice() {
        return mPrice;
    }

    public void setmPrice(float mPrice) {
        this.mPrice = mPrice;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    @Exclude
    public String getmKey() {
        return mKey;
    }

    @Exclude
    public void setmKey(String mKey) {
        this.mKey = mKey;
    }
}
