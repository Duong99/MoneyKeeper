package vn.com.nghiemduong.moneykeeper.ui.main.plus.choosecategories.categoryloan;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import vn.com.nghiemduong.moneykeeper.ui.main.plus.choosecategories.ChooseCategoriesActivity;
import vn.com.nghiemduong.moneykeeper.ui.main.plus.choosecategories.categorycollect.CategoryCollectFragmentPresenter;

/**
 * -
 * Màn hình chọn hạng mục thu nợ
 * <p>
 * - @created_by nxduong on 27/1/2021
 **/
public class CategoryLoanFragment extends BaseFragment implements CategoryLoanFragmentMvpView,
        CategoryAdapter.IOnClickCategory {
    private View mView;
    private RecyclerView rcvCategoryLoan;
    private CategoryAdapter mCategoryAdapter;
    private CategoryLoanFragmentPresenter mCategoryLoanFragmentPresenter;
    private ChooseCategoriesActivity mChooseCategoriesActivity;

    public CategoryLoanFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_category_loan, container, false);
        init();
        return mView;
    }

    private void init() {
        rcvCategoryLoan = mView.findViewById(R.id.rcvCategoryLoan);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rcvCategoryLoan.setLayoutManager(layoutManager);

        mCategoryLoanFragmentPresenter =
                new CategoryLoanFragmentPresenter(this, getContext());
        mCategoryLoanFragmentPresenter.getListCategoryLoan();
    }

    @Override
    public void resultListCategoryLoan(ArrayList<Category> listCategory) {
        mCategoryAdapter = new CategoryAdapter(getContext(), listCategory,
                this, CategoryAdapter.NO_SUBCATEGORY);
        rcvCategoryLoan.setAdapter(mCategoryAdapter);
    }

    @Override
    public void onClickCategoryPay(Category category) {
        mChooseCategoriesActivity.onFinishChooseCategory(
                new Category(category.getImage(), category.getTitle()));
    }

    @Override
    public void onClickSubCategoryPay(SubCategory subCategory) {

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mChooseCategoriesActivity = (ChooseCategoriesActivity) context;
    }
}