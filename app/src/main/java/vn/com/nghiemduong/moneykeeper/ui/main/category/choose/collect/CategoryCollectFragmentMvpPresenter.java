package vn.com.nghiemduong.moneykeeper.ui.main.category.choose.collect;

import android.content.Context;

import vn.com.nghiemduong.moneykeeper.data.model.db.Category;

/**
 * -
 * - @created_by nxduong on 10/3/2021
 **/
public interface CategoryCollectFragmentMvpPresenter {
    void getListCategoriesAndPositionSmooth(Category category, Context context);
}
