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

import vn.com.nghiemduong.moneykeeper.adapter.CategoryContainSubCategoryAdapter;
import vn.com.nghiemduong.moneykeeper.data.db.category.CategoryDatabase;
import vn.com.nghiemduong.moneykeeper.data.model.db.Category;
import vn.com.nghiemduong.moneykeeper.ui.base.BaseFragment;
import vn.com.nghiemduong.moneykeeper.ui.main.category.choose.ChooseCategoryActivity;
import vn.com.nghiemduong.moneykeeper.utils.AppUtils;

/**
 * -
 * Màn hình chọn hạng mục chi tiền
 * <p>
 * - @created_by nxduong on 27/1/2021
 **/
public class CategoryPayFragment extends BaseFragment
        implements CategoryContainSubCategoryAdapter.IOnClickCategory {
    private View mView;
    private RecyclerView rcvCategoryPay;
    private ChooseCategoryActivity mChooseCategoryActivity;

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
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(getContext());
        rcvCategoryPay.setLayoutManager(layoutManager);
        rcvCategoryPay.setAdapter(new CategoryContainSubCategoryAdapter(getContext(),
                new CategoryDatabase(getContext()).getAllParentCategory(AppUtils.CHI_TIEN,
                        AppUtils.CAP_DO_1), this));
    }

    // Khởi tạo / Ánh xạ
    private void init() {
        rcvCategoryPay = mView.findViewById(R.id.rcvCategoryPay);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        mChooseCategoryActivity = (ChooseCategoryActivity) context;
        super.onAttach(context);
    }

    @Override
    public void onClickCategory(Category category) {
        mChooseCategoryActivity.onFinishChooseCategory(category);
    }
}