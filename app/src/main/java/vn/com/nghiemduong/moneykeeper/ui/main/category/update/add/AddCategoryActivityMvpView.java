package vn.com.nghiemduong.moneykeeper.ui.main.category.update.add;

import vn.com.nghiemduong.moneykeeper.data.model.db.Category;

/**
 * - @created_by nxduong on 8/2/2021
 **/
public interface AddCategoryActivityMvpView {
    void resultGetBundleParentCategory(Category parentCategory);

    void resultGetStatusCategory(int status);
}
