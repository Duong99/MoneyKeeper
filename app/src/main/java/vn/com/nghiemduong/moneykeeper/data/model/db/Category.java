package vn.com.nghiemduong.moneykeeper.data.model.db;

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
    private int categoryParentId; // Id thể loại cha của hạng mục, nếu null hạng mục chính là cha

    public Category(int categoryId, String categoryName, String categoryPath, String explain,
                    int type, int categoryParentId) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryPath = categoryPath;
        this.explain = explain;
        this.type = type;
        this.categoryParentId = categoryParentId;
    }

    public Category(String categoryName, String categoryPath, String explain,
                    int type, int categoryParentId) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryPath = categoryPath;
        this.explain = explain;
        this.type = type;
        this.categoryParentId = categoryParentId;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getCategoryParentId() {
        return categoryParentId;
    }

    public void setCategoryParentId(int categoryParentId) {
        this.categoryParentId = categoryParentId;
    }
}
