package vn.com.nghiemduong.moneykeeper.data.db.category;

import java.util.ArrayList;

import vn.com.nghiemduong.moneykeeper.data.model.Category;
import vn.com.nghiemduong.moneykeeper.data.model.SubCategory;

/**
 * - @created_by nxduong on 6/2/2021
 **/
public interface SubCategoryDatabaseMvpPresenter {
    ArrayList<SubCategory> getAllSubCategory(int categoryId);

    SubCategory getSubCategory(int subCategoryId);

    long insertSubCategory(SubCategory subCategory);

    long updateSubCategory(SubCategory subCategory);

    long deleteSubCategory(int subCategoryId);
}
