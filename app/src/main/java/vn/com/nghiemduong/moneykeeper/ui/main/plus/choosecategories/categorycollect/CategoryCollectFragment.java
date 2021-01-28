package vn.com.nghiemduong.moneykeeper.ui.main.plus.choosecategories.categorycollect;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import vn.com.nghiemduong.moneykeeper.R;
import vn.com.nghiemduong.moneykeeper.adapter.CategoryAdapter;
import vn.com.nghiemduong.moneykeeper.data.model.Category;
import vn.com.nghiemduong.moneykeeper.data.model.SubCategory;
import vn.com.nghiemduong.moneykeeper.ui.base.BaseFragment;

/**
 * -  Màn hình chọn hạng mục thu tiền
 * <p>
 * <p>
 * - @created_by nxduong on 27/1/2021
 **/
public class CategoryCollectFragment extends BaseFragment implements CategoryCollectFragmentMvpView,
        CategoryAdapter.IOnClickCategory {

    private View mView;
    private RecyclerView rcvCategoryCollect;
    private CategoryAdapter mCategoryAdapter;
    private CategoryCollectFragmentMvpPresenter mCollectFragmentMvpPresenter;

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
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rcvCategoryCollect.setLayoutManager(layoutManager);

        mCollectFragmentMvpPresenter = new CategoryCollectFragmentPresenter(this);
        mCollectFragmentMvpPresenter.getListCategoriesCollect();
    }

    @Override
    public void resultListCategoriesCollect(ArrayList<Category> listCategories) {

        mCategoryAdapter = new CategoryAdapter(getContext(), listCategories, this,
                CategoryAdapter.NO_SUBCATEGORY);
        rcvCategoryCollect.setAdapter(mCategoryAdapter);

    }

    @Override
    public void onClickCategoryPay(Category category) {

    }

    @Override
    public void onClickSubCategoryPay(SubCategory subCategory) {

    }
}