package vn.com.nghiemduong.moneykeeper.ui.main.category.choose.loan;

import android.content.Context;

/**
 * - @created_by nxduong on 28/1/2021
 **/
public class CategoryLoanFragmentPresenter implements CategoryLoanFragmentMvpPresenter {
    private CategoryLoanFragmentMvpView mCategoryLoanFragmentMvpView;
    private Context mContext;

    public CategoryLoanFragmentPresenter(CategoryLoanFragmentMvpView categoryLoanFragmentMvpView,
                                         Context context) {
        this.mCategoryLoanFragmentMvpView = categoryLoanFragmentMvpView;
        this.mContext = context;
    }

}
