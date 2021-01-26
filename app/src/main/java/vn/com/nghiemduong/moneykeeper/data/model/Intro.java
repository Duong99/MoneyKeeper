package vn.com.nghiemduong.moneykeeper.data.model;

/**
 * Giói thiệu trong ứng ựng
 * - @created_by nxduong on
 **/
public class Intro {
    private int picture;
    private String title;

    public Intro(int picture, String title) {
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
