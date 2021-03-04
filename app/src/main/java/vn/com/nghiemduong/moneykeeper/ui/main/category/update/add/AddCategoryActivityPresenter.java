package vn.com.nghiemduong.moneykeeper.ui.main.category.update.add;

import android.app.Activity;

import java.util.Objects;

import vn.com.nghiemduong.moneykeeper.data.model.db.Category;

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

}
