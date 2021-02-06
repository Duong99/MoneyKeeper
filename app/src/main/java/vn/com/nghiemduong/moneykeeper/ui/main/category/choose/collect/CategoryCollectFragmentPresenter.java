package vn.com.nghiemduong.moneykeeper.ui.main.category.choose.collect;

import android.content.Context;

/**
 * - @created_by nxduong on 28/1/2021
 **/
public class CategoryCollectFragmentPresenter implements CategoryCollectFragmentMvpPresenter {
    private CategoryCollectFragmentMvpView mCategoryCollectFragmentMvpView;
    private Context mContext;

    public CategoryCollectFragmentPresenter(CategoryCollectFragmentMvpView categoryCollectFragmentMvpView,
                                            Context context) {
        this.mCategoryCollectFragmentMvpView = categoryCollectFragmentMvpView;
        this.mContext = context;
    }
}
