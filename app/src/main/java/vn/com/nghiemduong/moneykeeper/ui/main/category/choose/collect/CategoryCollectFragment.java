package vn.com.nghiemduong.moneykeeper.ui.main.category.choose.collect;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import vn.com.nghiemduong.moneykeeper.R;
import vn.com.nghiemduong.moneykeeper.adapter.CategoryContainSubCategoryAdapter;
import vn.com.nghiemduong.moneykeeper.data.db.category.CategoryDatabase;
import vn.com.nghiemduong.moneykeeper.data.model.db.Category;
import vn.com.nghiemduong.moneykeeper.ui.base.BaseFragment;
import vn.com.nghiemduong.moneykeeper.ui.main.category.choose.ChooseCategoryActivity;
import vn.com.nghiemduong.moneykeeper.utils.AppConstants;
import vn.com.nghiemduong.moneykeeper.utils.AppUtils;

/**
 * -  Màn hình chọn hạng mục thu tiền
 * <p>
 * <p>
 * - @created_by nxduong on 27/1/2021
 **/
public class CategoryCollectFragment extends BaseFragment
        implements CategoryContainSubCategoryAdapter.IOnClickCategory, CategoryCollectFragmentMvpView {

    private View mView;
    private RecyclerView rcvCategoryCollect;
    private CategoryCollectFragmentPresenter mCategoryCollectFragmentPresenter;

    private ChooseCategoryActivity mChooseCategoryActivity;

    public CategoryCollectFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_category_collect, container, false);
        init();
        return mView;
    }

    // Khởi tạo / ánh xạ
    private void init() {
        rcvCategoryCollect = mView.findViewById(R.id.rcvCategoryCollect);
        mCategoryCollectFragmentPresenter = new CategoryCollectFragmentPresenter(this);
        mCategoryCollectFragmentPresenter.getListCategoriesAndPositionSmooth(
                mChooseCategoryActivity.getCategory(), getContext());

    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mChooseCategoryActivity = (ChooseCategoryActivity) context;
    }

    @Override
    public void onClickCategory(Category category) {
        mChooseCategoryActivity.onFinishChooseCategory(category);
    }

    @Override
    public void resultListCategoriesPayAndPositionSmooth(ArrayList<Category> listCategoriesPay, int positionSmooth) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rcvCategoryCollect.setLayoutManager(layoutManager);
        rcvCategoryCollect.setAdapter(new CategoryContainSubCategoryAdapter(getContext(),
                listCategoriesPay, this, mChooseCategoryActivity.getCategory()));
        if (positionSmooth != -1) {
            rcvCategoryCollect.smoothScrollToPosition(positionSmooth);
        }
    }
}