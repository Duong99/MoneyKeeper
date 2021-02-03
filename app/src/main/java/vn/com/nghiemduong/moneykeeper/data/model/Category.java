package vn.com.nghiemduong.moneykeeper.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * D
 * - @created_by nxduong on 27/1/2021
 **/
public class Category implements Serializable {

    private String imagePath;

    private String title;

    private ArrayList<SubCategory> subCategories;

    // Hàm tạo có hạng mục con
    public Category(String imagePath, String title, ArrayList<SubCategory> subCategories) {
        this.imagePath = imagePath;
        this.title = title;
        this.subCategories = subCategories;
    }

    // Hàm tạo không có hạng mục con
    public Category(String imagePath, String title) {
        this.imagePath = imagePath;
        this.title = title;
    }

    public String getImage() {
        return imagePath;
    }

    public void setImage(String image) {
        this.imagePath = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<SubCategory> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(ArrayList<SubCategory> subCategories) {
        this.subCategories = subCategories;
    }
}
