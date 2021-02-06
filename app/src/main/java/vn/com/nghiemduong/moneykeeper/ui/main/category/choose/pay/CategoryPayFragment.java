package vn.com.nghiemduong.moneykeeper.ui.main.category.choose.pay;

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
        setUpRecyclerViewCategory();
        return mView;
    }


    /**
     * Hàm lấy danh sách hạng mục trong database và đổ lên Recycelrview
     *
     * @created_by nxduong on 6/2/2021
     */
    private void setUpRecyclerViewCategory() {
        mCategoryAdapter = new CategoryAdapter(getContext(),
                mChooseCategoriesActivity.getListCategories(AppUtils.CHI_TIEN), this);
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(getContext());
        rcvCategoryPay.setLayoutManager(layoutManager);
        rcvCategoryPay.setAdapter(mCategoryAdapter);
    }

    // Khởi tạo / Ánh xạ
    private void init() {
        rcvCategoryPay = mView.findViewById(R.id.rcvCategoryPay);

        mCategoryPayFragmentPresenter =
                new CategoryPayFragmentPresenter(this, getContext());
        mCategoryPayFragmentPresenter.getListCategoryPay();
    }

    @Override
    public void onClickCategoryPay(Category category) {
//        mChooseCategoriesActivity.onFinishChooseCategory(
//                new Category(category.getCategoryPath(), category.getCategoryName()));
    }

    @Override
    public void onClickSubCategoryPay(SubCategory subCategory) {
//        mChooseCategoriesActivity.onFinishChooseCategory(
//                new Category(subCategory.getPicture(), subCategory.getTitle()));
    }

    @Override
    public void onAttach(@NonNull Context context) {
        mChooseCategoriesActivity = (ChooseCategoriesActivity) context;
        super.onAttach(context);
    }

}