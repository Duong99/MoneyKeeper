package vn.com.nghiemduong.moneykeeper.data.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * D
 * - @created_by nxduong on 27/1/2021
 **/
public class Category implements Serializable {

    private int categoryId; //(INTEGER) : id của hạng mục (category)
    private String categoryName;//(TEXT) :Tên của hạng mục (category)
    private String categoryPath; //(TEXT) : tên file ảnh
    private String explain; //(TEXT): chú giải
    private int type; // (INTEGER) lại hạng mục Chi Tiền (-1), Thu Tiền (1), Vay nợ (0)
    private ArrayList<SubCategory> subCategories;

    public Category(int categoryId, String categoryName, String categoryPath, String explain,
                    int type, ArrayList<SubCategory> subCategories) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryPath = categoryPath;
        this.explain = explain;
        this.type = type;
        this.subCategories = subCategories;
    }

    public Category(int categoryId, String categoryName, String categoryPath, String explain, int type) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryPath = categoryPath;
        this.explain = explain;
        this.type = type;
    }

    public Category(String categoryName, String categoryPath, String explain, int type) {
        this.categoryName = categoryName;
        this.categoryPath = categoryPath;
        this.explain = explain;
        this.type = type;
    }

    /**
     * Dùng hiển thị lên recycler chỉ hiển thị ảnh
     *
     * @param categoryPath đường dẫn icon từ folder asset
     *                     - {@link vn.com.nghiemduong.moneykeeper.adapter.IconCategoryAdapter}
     * @created_by nxduong on
     */
    public Category(String categoryPath) {
        this.categoryPath = categoryPath;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryPath() {
        return categoryPath;
    }

    public void setCategoryPath(String categoryPath) {
        this.categoryPath = categoryPath;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public ArrayList<SubCategory> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(ArrayList<SubCategory> subCategories) {
        this.subCategories = subCategories;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
