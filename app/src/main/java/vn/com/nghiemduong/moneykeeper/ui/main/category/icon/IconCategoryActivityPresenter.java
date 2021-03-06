package vn.com.nghiemduong.moneykeeper.ui.main.category.icon;

import android.content.Context;

import java.io.IOException;
import java.util.ArrayList;

import vn.com.nghiemduong.moneykeeper.data.model.db.Category;

/**
 * - @created_by nxduong on 7/2/2021
 **/
public class IconCategoryActivityPresenter implements IconCategoryActivityMvpPresenter {
    private IconCategoryActivityMvpView mIconCategoryActivityMvpView;

    public IconCategoryActivityPresenter(IconCategoryActivityMvpView iconCategoryActivityMvpView) {
        this.mIconCategoryActivityMvpView = iconCategoryActivityMvpView;
    }

    /**
     * Hàm lấy danh sách dường dẫn icon của hạng mục
     *
     * @param path đường dẫn chứa danh sách đường dẫn icon
     * @created_by nxduong on 7/2/2021
     * @see
     */

    @Override
    public void doGetPathCategoryFromAssets(String path, Context context) {
        ArrayList<Category> listCategoriesPath = new ArrayList<>();
        try {
            String[] categoriesPath = context.getAssets().list(path);
            for (String s : categoriesPath) {
                listCategoriesPath.add(new Category(path + s));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        mIconCategoryActivityMvpView.onFinishGetPathCategoryFromAsset(listCategoriesPath);
    }
}
