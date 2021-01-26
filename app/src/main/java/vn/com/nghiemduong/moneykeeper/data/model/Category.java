package vn.com.nghiemduong.moneykeeper.data.model;

/**
 * - @created_by nxduong on 25/1/2021
 **/
public class Category {
    private int picture;
    private String title;

    public Category(int picture, String title) {
        this.picture = picture;
        this.title = title;
    }

    public int getPicture() {
        return picture;
    }

    public void setPicture(int picture) {
        this.picture = picture;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
