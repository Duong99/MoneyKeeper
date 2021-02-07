package vn.com.nghiemduong.moneykeeper.ui.main.category.icon;

import java.util.ArrayList;

import vn.com.nghiemduong.moneykeeper.data.model.Category;

/**
 * - @created_by nxduong on 7/2/2021
 **/
public interface IconCategoryActivityMvpView {
    void onFinishGetPathCategoryFromAsset(ArrayList<Category> listCategoriesPath);
}
