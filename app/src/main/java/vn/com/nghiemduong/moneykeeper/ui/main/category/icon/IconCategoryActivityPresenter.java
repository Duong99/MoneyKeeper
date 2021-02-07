package vn.com.nghiemduong.moneykeeper.ui.main.category.icon;

import android.content.Context;

import java.io.IOException;

/**
 * - @created_by nxduong on 7/2/2021
 **/
public class IconCategoryActivityPresenter implements IconCategoryActivityMvpPresenter {
    private IconCategoryActivityMvpView mIconCategoryActivityMvpView;

    public IconCategoryActivityPresenter(IconCategoryActivityMvpView iconCategoryActivityMvpView) {
        this.mIconCategoryActivityMvpView = iconCategoryActivityMvpView;
    }

    @Override
    public void doGetPathCategoryFromAssets(String path, Context context) {
        try {
            context.getAssets().list(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
