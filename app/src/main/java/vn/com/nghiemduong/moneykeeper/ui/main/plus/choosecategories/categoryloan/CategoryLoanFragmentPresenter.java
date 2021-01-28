package vn.com.nghiemduong.moneykeeper.ui.main.plus.choosecategories.categoryloan;

import android.content.Context;

import java.util.ArrayList;

import vn.com.nghiemduong.moneykeeper.R;
import vn.com.nghiemduong.moneykeeper.data.model.Category;
import vn.com.nghiemduong.moneykeeper.utils.AppUtils;

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

    /**
     * -  Hàm tạo đối tượng hạng mục vay nợ
     * <p>
     * - @created_by nxduong on 28/1/2021
     **/
    @Override
    public void getListCategoryLoan() {
        ArrayList<Category> listCategories = new ArrayList<>();
        String path = "assets/ImageCategory/THU/";
        listCategories.add(new Category(AppUtils.getByteImageFromAssets(path + "THU_cho_vay.png",
                mContext), mContext.getResources().getString(R.string.loan)));
        listCategories.add(new Category(AppUtils.getByteImageFromAssets(path + "THU_di_vay.png",
                mContext), mContext.getResources().getString(R.string.borrow)));
        listCategories.add(new Category(AppUtils.getByteImageFromAssets(path + "THU_thu_no.png",
                mContext), mContext.getResources().getString(R.string.debt_collection)));
        listCategories.add(new Category(AppUtils.getByteImageFromAssets(path + "THU_tra_no.png",
                mContext), mContext.getResources().getString(R.string.debt_pay)));

        mCategoryLoanFragmentMvpView.resultListCategoryLoan(listCategories);
    }
}
