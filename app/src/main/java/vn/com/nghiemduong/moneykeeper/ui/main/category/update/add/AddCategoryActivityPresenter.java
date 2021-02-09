package vn.com.nghiemduong.moneykeeper.ui.main.category.update.add;

import android.app.Activity;
import android.content.Context;

import java.util.Objects;

import vn.com.nghiemduong.moneykeeper.data.model.Category;
import vn.com.nghiemduong.moneykeeper.data.model.SubCategory;
import vn.com.nghiemduong.moneykeeper.ui.main.accountoverview.add.AddAccountActivityMvpView;

/**
 * - @created_by nxduong on 8/2/2021
 **/
public class AddCategoryActivityPresenter implements AddCategoryActivityMvpPresenter {
    private AddCategoryActivityMvpView mAddCategoryActivityMvpView;
    private Activity mActivity;

    public AddCategoryActivityPresenter(AddCategoryActivityMvpView addCategoryActivityMvpView,
                                        Activity activity) {
        this.mAddCategoryActivityMvpView = addCategoryActivityMvpView;
        this.mActivity = activity;
    }

    /**
     * Lấy giá trị hạng mục cha gửi lên cần sửa xóa
     *
     * @created_by nxduong on 9/2/2021
     */
    @Override
    public void doGetBundleParentCategory() {
        Category parentCategory = (Category) Objects.requireNonNull(mActivity.getIntent()
                .getBundleExtra("BUNDLE")).getSerializable("BUNDLE_CATEGORY_PARENT");
        mAddCategoryActivityMvpView.resultGetBundleParentCategory(parentCategory);
    }

    /**
     * Lấy giá trị hạng mục con gửi lên cần sửa,xóa
     *
     * @created_by nxduong on 9/2/2021
     */
    @Override
    public void doGetBundleSubCategory() {
        SubCategory subCategory = (SubCategory) Objects.requireNonNull(mActivity.getIntent()
                .getBundleExtra("BUNDLE")).getSerializable("BUNDLE_CATEGORY_SUB");
        mAddCategoryActivityMvpView.resultGetBundleSubCategory(subCategory);
    }

    /**
     * Lấy giá trị trạng thái được gửi lên thêm , sửa, xóa
     *
     * @created_by nxduong on
     */

    @Override
    public void doGetStatusUpdateCategory() {
        int status = mActivity.getIntent().getIntExtra(AddCategoryActivity.VALUE_STATUS, -1);
        mAddCategoryActivityMvpView.resultGetStatusCategory(status);
    }
}
