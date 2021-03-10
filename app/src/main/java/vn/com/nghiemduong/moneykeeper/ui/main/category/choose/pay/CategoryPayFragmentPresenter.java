package vn.com.nghiemduong.moneykeeper.ui.main.category.choose.pay;


import android.content.Context;

import java.util.ArrayList;

import vn.com.nghiemduong.moneykeeper.data.db.category.CategoryDatabase;
import vn.com.nghiemduong.moneykeeper.data.model.db.Category;
import vn.com.nghiemduong.moneykeeper.utils.AppConstants;

/**
 * -
 * - @created_by nxduong on 10/3/2021
 **/
public class CategoryPayFragmentPresenter implements CategoryPayFragmentMvpPresenter {
    CategoryPayFragmentMvpView mCategoryPayFragmentMvpView;

    public CategoryPayFragmentPresenter(CategoryPayFragmentMvpView categoryPayFragmentMvpView) {
        this.mCategoryPayFragmentMvpView = categoryPayFragmentMvpView;
    }

    @Override
    public void doGetListCategoryPayAndPositionSmooth(Category category, Context context) {
        ArrayList<Category> listCategories = new CategoryDatabase(context).getAllParentCategory(
                AppConstants.CHI_TIEN, AppConstants.CAP_DO_1);
        int positionSmooth = -1;

        if (category != null) {
            for (int i = 0; i < listCategories.size(); i++) {
                if (category.getCategoryId() == listCategories.get(i).getCategoryId()) {
                    positionSmooth = i;
                    break;
                }
            }
        }

        mCategoryPayFragmentMvpView.resultListCategoryPay(listCategories, positionSmooth);
    }
}
