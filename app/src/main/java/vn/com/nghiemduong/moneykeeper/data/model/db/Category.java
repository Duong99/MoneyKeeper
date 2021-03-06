package vn.com.nghiemduong.moneykeeper.data.model.db;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

/**
 * D
 * - @created_by nxduong on 27/1/2021
 **/
public class Category implements Serializable {

    private int categoryId; //(INTEGER) : id của hạng mục (category)
    private String categoryName;//(TEXT) :Tên của hạng mục (category)
    private String categoryPath; //(TEXT) : tên file ảnh
    private String description; //(TEXT): chú giải
    private int type; // (INTEGER) lại hạng mục Chi Tiền (-1), Thu Tiền (1), Vay nợ (0)
    private int categoryParentId; // Id thể loại cha của hạng mục, nếu null hạng mục chính là cha
    private int level; // cấp độ của hạng mục có 2 cấp độ 1 và 2

    public Category(int categoryId, String categoryName, String categoryPath, String description,
                    int type, int categoryParentId, int level) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryPath = categoryPath;
        this.description = description;
        this.type = type;
        this.categoryParentId = categoryParentId;
        this.level = level;
    }

    public Category(String categoryName, String categoryPath, String description,
                    int type, int categoryParentId, int level) {
        this.categoryName = categoryName;
        this.categoryPath = categoryPath;
        this.description = description;
        this.type = type;
        this.categoryParentId = categoryParentId;
        this.level = level;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

}
