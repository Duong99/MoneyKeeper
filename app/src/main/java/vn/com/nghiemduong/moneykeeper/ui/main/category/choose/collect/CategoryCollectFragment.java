package vn.com.nghiemduong.moneykeeper.ui.main.category.choose.collect;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.com.nghiemduong.moneykeeper.R;
import vn.com.nghiemduong.moneykeeper.adapter.CategoryAdapter;
import vn.com.nghiemduong.moneykeeper.data.model.Category;
import vn.com.nghiemduong.moneykeeper.data.model.SubCategory;
import vn.com.nghiemduong.moneykeeper.ui.base.BaseFragment;
import vn.com.nghiemduong.moneykeeper.ui.main.category.choose.ChooseCategoriesActivity;
import vn.com.nghiemduong.moneykeeper.utils.AppUtils;

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
    private ChooseCategoriesActivity mChooseCategoriesActivity;

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

        mCategoryAdapter = new CategoryAdapter(getContext(),
                mChooseCategoriesActivity.getListCategories(AppUtils.THU_TIEN), this);
        rcvCategoryCollect.setAdapter(mCategoryAdapter);
    }


    @Override
    public void onClickCategoryPay(Category category) {
//        mChooseCategoriesActivity.onFinishChooseCategory(
//                new Category(category.getImage(), category.getTitle()));
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