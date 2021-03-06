package vn.com.nghiemduong.moneykeeper.ui.main.category.update.add;

import android.content.Context;
import android.content.Intent;

import vn.com.nghiemduong.moneykeeper.data.model.db.Category;

/**
 * - @created_by nxduong on 8/2/2021
 **/
public interface AddCategoryActivityMvpPresenter {
    void getCategoryPathWhenChooseIcon(Intent data);

    void getParentCategory(Intent data);

    void insertCategory(Category category, Context context);

    void getCategoryFromBundle();
}
