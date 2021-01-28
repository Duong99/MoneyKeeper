package vn.com.nghiemduong.moneykeeper.ui.main.plus.choosecategories.categorycollect;

import android.content.Context;

import java.util.ArrayList;

import vn.com.nghiemduong.moneykeeper.R;
import vn.com.nghiemduong.moneykeeper.data.model.Category;
import vn.com.nghiemduong.moneykeeper.utils.AppUtils;

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

    @Override
    public void getListCategoriesCollect() {
        ArrayList<Category> listCategories = new ArrayList<>();
        String path = "assets/ImageCategory/THU/";
        listCategories.add(new Category(AppUtils.getByteImageFromAssets(path + "THU_bieu_tang.png",
                mContext), mContext.getResources().getString(R.string.send_donate)));
        listCategories.add(new Category(AppUtils.getByteImageFromAssets(path + "THU_khac.png",
                mContext), mContext.getResources().getString(R.string.other)));
        listCategories.add(new Category(AppUtils.getByteImageFromAssets(path + "THU_lai_tiet_kiem.png",
                mContext), mContext.getResources().getString(R.string.saving_interest)));
        listCategories.add(new Category(AppUtils.getByteImageFromAssets(path + "THU_luong.png",
                mContext), mContext.getResources().getString(R.string.salary)));
        listCategories.add(new Category(AppUtils.getByteImageFromAssets(path + "THU_thuong.png",
                mContext), mContext.getResources().getString(R.string.reward)));
        listCategories.add(new Category(AppUtils.getByteImageFromAssets(path + "THU_tien_lai.png",
                mContext), mContext.getResources().getString(R.string.interest)));
        mCategoryCollectFragmentMvpView.resultListCategoriesCollect(listCategories);
    }


}
