package vn.com.nghiemduong.moneykeeper.ui.main.category.update.add;

import vn.com.nghiemduong.moneykeeper.data.model.db.Category;

/**
 * - @created_by nxduong on 8/2/2021
 **/
public interface AddCategoryActivityMvpView {
    void resultGetCategoryPathWhenChooseIcon(String pathCategory);

    void resultGetParentCategory(Category parentCategory);

    void insertCategorySuccess();

    void insertCategoryFail();

    void updateCategorySuccess();

    void updateCategoryFail();

    void resultGetCategoryFromBundle(Category category);
}
