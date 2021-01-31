package vn.com.nghiemduong.moneykeeper.data.model;

/**
 * -  Đối tượng báo cáo
 * <p>
 * <p>
 * - @created_by nxduong on 25/1/2021
 **/
public class Report {
    private int image;
    private String title;

    public Report(int image, String title) {
        this.image = image;
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
