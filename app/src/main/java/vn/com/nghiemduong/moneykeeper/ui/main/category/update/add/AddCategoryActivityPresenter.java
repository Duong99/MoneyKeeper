package vn.com.nghiemduong.moneykeeper.ui.main.category.update.add;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.Objects;

import vn.com.nghiemduong.moneykeeper.data.db.category.CategoryDatabase;
import vn.com.nghiemduong.moneykeeper.data.model.db.Category;
import vn.com.nghiemduong.moneykeeper.utils.DBUtils;

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

    @Override
    public void getCategoryPathWhenChooseIcon(Intent data) {
        Category category = (Category) Objects.requireNonNull(data.getBundleExtra("BUNDLE"))
                .getSerializable("BUNDLE_ICON_CATEGORY");
        if (category != null) {
            mAddCategoryActivityMvpView.resultGetCategoryPathWhenChooseIcon(category.getCategoryPath());
        }
    }

    @Override
    public void getParentCategory(Intent data) {
        Category parentCategory = (Category) Objects.requireNonNull(data.getBundleExtra("BUNDLE"))
                .getSerializable("BUNDLE_PARENT_CATEGORY");
        mAddCategoryActivityMvpView.resultGetParentCategory(parentCategory);
    }

    @Override
    public void insertCategory(Category category, Context context) {
        long insert = new CategoryDatabase(context).insertCategory(category);
        if (insert == DBUtils.checkDBFail) {
            mAddCategoryActivityMvpView.insertCategoryFail();
        } else {
            mAddCategoryActivityMvpView.insertCategorySuccess();
        }
    }

    @Override
    public void updateCategory(Category category, Context context) {
        long update = new CategoryDatabase(context).updateCategory(category);
        if (update == DBUtils.checkDBFail) {
            mAddCategoryActivityMvpView.updateCategoryFail();
        } else {
            mAddCategoryActivityMvpView.updateCategorySuccess();
        }
    }

    @Override
    public void getCategoryFromBundle() {
        Category category = (Category) Objects.requireNonNull(mActivity.getIntent()
                .getBundleExtra("BUNDLE")).getSerializable("BUNDLE_CATEGORY");
        mAddCategoryActivityMvpView.resultGetCategoryFromBundle(category);
    }
}
