package vn.com.nghiemduong.moneykeeper.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * - @created_by nxduong on 27/1/2021
 **/
public class SubCategory implements Serializable {
    private byte[] picture;
    private String title;

    public SubCategory(byte[] picture, String title) {
        this.picture = picture;
        this.title = title;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
