package vn.com.nghiemduong.moneykeeper.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * - @created_by nxduong on 27/1/2021
 **/
public class SubCategory implements Serializable {
    private String picturePath;
    private String title;

    public SubCategory(String picturePath, String title) {
        this.picturePath = picturePath;
        this.title = title;
    }

    public String getPicture() {
        return picturePath;
    }

    public void setPicture(String picturePath) {
        this.picturePath = picturePath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
