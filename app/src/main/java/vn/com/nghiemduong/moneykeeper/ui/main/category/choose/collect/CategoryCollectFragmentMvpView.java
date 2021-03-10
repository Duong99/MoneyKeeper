package vn.com.nghiemduong.moneykeeper.ui.main.category.choose.collect;

import java.util.ArrayList;

import vn.com.nghiemduong.moneykeeper.data.model.db.Category;

/**
 * -
 * - @created_by nxduong on 10/3/2021
 **/
public interface CategoryCollectFragmentMvpView {
    void resultListCategoriesPayAndPositionSmooth(ArrayList<Category> listCategoriesPay, int positionSmooth);
}
