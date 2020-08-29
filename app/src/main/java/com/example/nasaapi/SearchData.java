package com.example.nasaapi;

public class SearchData {
    public String mTitle;
    public String mDescription;
    public String mNasa_id;

    public SearchData(String mTitle, String mDescription, String mNasa_id) {
        this.mTitle = mTitle;
        this.mDescription = mDescription;
        this.mNasa_id = mNasa_id;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getmNasa_id() {
        return mNasa_id;
    }

    public void setmNasa_id(String mNasa_id) {
        this.mNasa_id = mNasa_id;
    }
}
