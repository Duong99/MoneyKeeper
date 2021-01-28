package vn.com.nghiemduong.moneykeeper.ui.main.plus.choosecategories.categorypay;

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
import vn.com.nghiemduong.moneykeeper.adapter.CategoryAdapter;
import vn.com.nghiemduong.moneykeeper.data.model.Category;

import vn.com.nghiemduong.moneykeeper.data.model.SubCategory;
import vn.com.nghiemduong.moneykeeper.ui.base.BaseFragment;
import vn.com.nghiemduong.moneykeeper.ui.main.plus.choosecategories.ChooseCategoriesActivity;

/**
 * -
 * Màn hình chọn hạng mục chi tiền
 * <p>
 * - @created_by nxduong on 27/1/2021
 **/
public class CategoryPayFragment extends BaseFragment implements CategoryPayFragmentMvpView
        , CategoryAdapter.IOnClickCategory {
    private View mView;
    private RecyclerView rcvCategoryPay;
    private CategoryAdapter mCategoryAdapter;
    private CategoryPayFragmentPresenter mCategoryPayFragmentPresenter;
    private ChooseCategoriesActivity mChooseCategoriesActivity;

    public CategoryPayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_category_pay, container, false);
        init();
        return mView;
    }

    // Khởi tạo / Ánh xạ
    private void init() {
        rcvCategoryPay = mView.findViewById(R.id.rcvCategoryPay);

        mCategoryPayFragmentPresenter =
                new CategoryPayFragmentPresenter(this, getContext());
        mCategoryPayFragmentPresenter.getListCategoryPay();
    }

    @Override
    public void resultCategoryPay(ArrayList<Category> listCategories) {
        mCategoryAdapter = new CategoryAdapter(getContext(), listCategories, this,
                CategoryAdapter.SUBCATEGORY);
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(getContext());
        rcvCategoryPay.setLayoutManager(layoutManager);
        rcvCategoryPay.setAdapter(mCategoryAdapter);
    }

    @Override
    public void onClickCategoryPay(Category category) {
        mChooseCategoriesActivity.onFinishChooseCategory(
                new Category(category.getImage(), category.getTitle()));
    }

    @Override
    public void onClickSubCategoryPay(SubCategory subCategory) {
        mChooseCategoriesActivity.onFinishChooseCategory(
                new Category(subCategory.getPicture(), subCategory.getTitle()));
    }

    @Override
    public void onAttach(@NonNull Context context) {
        mChooseCategoriesActivity = (ChooseCategoriesActivity) context;
        super.onAttach(context);
    }
}