package vn.com.nghiemduong.moneykeeper.data.db.category;

import android.content.Context;

import java.util.ArrayList;

import vn.com.nghiemduong.moneykeeper.data.model.db.Category;

/**
 * - @created_by nxduong on 6/2/2021
 **/
public interface CategoryDatabaseMvpPresenter {
    ArrayList<Category> getAllCategory(int type, Context context);

    Category getCategory(int categoryId);

    long insertCategory(Category category);

    long updateCategory(Category category);

    long deleteCategory(int categoryId);
}
