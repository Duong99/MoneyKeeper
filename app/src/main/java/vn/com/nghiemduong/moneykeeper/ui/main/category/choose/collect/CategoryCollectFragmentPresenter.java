package vn.com.nghiemduong.moneykeeper.ui.main.category.choose.collect;

import android.content.Context;

import java.util.ArrayList;

import vn.com.nghiemduong.moneykeeper.data.db.category.CategoryDatabase;
import vn.com.nghiemduong.moneykeeper.data.model.db.Category;
import vn.com.nghiemduong.moneykeeper.utils.AppConstants;

/**
 * -
 * - @created_by nxduong on 10/3/2021
 **/
public class CategoryCollectFragmentPresenter implements CategoryCollectFragmentMvpPresenter {
    CategoryCollectFragmentMvpView mCategoryCollectFragmentMvpView;

    public CategoryCollectFragmentPresenter(CategoryCollectFragmentMvpView categoryCollectFragmentMvpView) {
        this.mCategoryCollectFragmentMvpView = categoryCollectFragmentMvpView;
    }

    @Override
    public void getListCategoriesAndPositionSmooth(Category category, Context context) {
        ArrayList<Category> listCategoriesPay =
                new CategoryDatabase(context).getAllParentCategory(AppConstants.THU_TIEN,
                        AppConstants.CAP_DO_1);
        int positionSmooth = -1;
        if (category != null) {
            for (int i = 0; i < listCategoriesPay.size(); i++) {
                if (category.getCategoryId() == listCategoriesPay.get(i).getCategoryId()) {
                    positionSmooth = i;
                    break;
                }
            }
        }
        mCategoryCollectFragmentMvpView.resultListCategoriesPayAndPositionSmooth(listCategoriesPay, positionSmooth);
    }
}
