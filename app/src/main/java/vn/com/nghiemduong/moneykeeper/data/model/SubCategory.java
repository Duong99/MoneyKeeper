package vn.com.nghiemduong.moneykeeper.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @created_by nxduong on 27/1/2021
 **/
public class SubCategory implements Serializable {
    private int subCategoryId;// (INTEGER) : id của category con
    private int categoryId;// (INTEGER) : id của category cha
    private String subCategoryName;//  (TEXT) Tên hạng mục con
    private String subCategoryPath;//  (TEXT) : Tên file ảnh
    private String explain;// (TEXT)

    public SubCategory(int subCategoryId, int categoryId, String subCategoryName,
                       String subCategoryPath, String explain) {
        this.subCategoryId = subCategoryId;
        this.categoryId = categoryId;
        this.subCategoryName = subCategoryName;
        this.subCategoryPath = subCategoryPath;
        this.explain = explain;
    }

    public SubCategory(int categoryId, String subCategoryName, String subCategoryPath, String explain) {
        this.categoryId = categoryId;
        this.subCategoryName = subCategoryName;
        this.subCategoryPath = subCategoryPath;
        this.explain = explain;
    }

    public int getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(int subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public String getSubCategoryPath() {
        return subCategoryPath;
    }

    public void setSubCategoryPath(String subCategoryPath) {
        this.subCategoryPath = subCategoryPath;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }
}
