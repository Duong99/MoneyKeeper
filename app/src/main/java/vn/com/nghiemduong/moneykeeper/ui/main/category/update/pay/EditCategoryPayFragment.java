package vn.com.nghiemduong.moneykeeper.ui.main.category.update.pay;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import vn.com.nghiemduong.moneykeeper.R;
import vn.com.nghiemduong.moneykeeper.adapter.CategoryEditContainSubCategoryAdapter;
import vn.com.nghiemduong.moneykeeper.data.db.category.CategoryDatabase;
import vn.com.nghiemduong.moneykeeper.data.model.db.Category;
import vn.com.nghiemduong.moneykeeper.ui.base.BaseFragment;
import vn.com.nghiemduong.moneykeeper.ui.main.category.update.UpdateCategoryActivity;
import vn.com.nghiemduong.moneykeeper.ui.main.category.update.add.AddCategoryActivity;
import vn.com.nghiemduong.moneykeeper.utils.AppConstants;
import vn.com.nghiemduong.moneykeeper.utils.AppUtils;

import static android.app.Activity.RESULT_OK;

/**
 * Màn hình sửa MỤC CHI
 * - @created_by nxduong on 6/2/2021
 **/

public class EditCategoryPayFragment extends BaseFragment
        implements UpdateCategoryActivity.IOnGetRestartCategoryPay,
        CategoryEditContainSubCategoryAdapter.IOnClickCategoryForEdit {

    private View mView;
    private RecyclerView rcvEditCategoryPay;
    private UpdateCategoryActivity mUpdateCategoryActivity;

    public EditCategoryPayFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_edit_category_pay, container, false);
        init();
        return mView;
    }

    private void init() {
        rcvEditCategoryPay = mView.findViewById(R.id.rcvEditCategoryPay);
        rcvEditCategoryPay.setLayoutManager(new LinearLayoutManager(getContext()));
        rcvEditCategoryPay.setAdapter(
                new CategoryEditContainSubCategoryAdapter(getContext(),
                        new CategoryDatabase(getContext()).getAllParentCategory(
                                AppConstants.CHI_TIEN, AppConstants.CAP_DO_1), this));
    }

    @Override
    public void onAttach(@NonNull Context context) {
        mUpdateCategoryActivity = (UpdateCategoryActivity) context;
        mUpdateCategoryActivity.setOnGetRestartCategoryPay(this);
        super.onAttach(context);
    }

    @Override
    public void onRestartCategoryPay() {
        rcvEditCategoryPay.setAdapter(
                new CategoryEditContainSubCategoryAdapter(getContext(),
                        new CategoryDatabase(getContext()).getAllParentCategory(
                                AppConstants.CHI_TIEN, AppConstants.CAP_DO_1), this));
    }


    @Override
    public void onClickCategoryEdit(Category category) {
        try {
            Intent intentEditCategory = new Intent(getContext(), AddCategoryActivity.class);
            Bundle bundle = new Bundle();
            intentEditCategory.putExtra("KEY_CATEGORY", AppConstants.CHI_TIEN);
            bundle.putSerializable("BUNDLE_CATEGORY", category);
            intentEditCategory.putExtra("BUNDLE", bundle);
            startActivityForResult(intentEditCategory, AddCategoryActivity.REQUEST_CODE_ADD_CATEGORY);
        } catch (Exception e) {
            AppUtils.handlerException(e);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (data != null) {
                if (requestCode == AddCategoryActivity.REQUEST_CODE_ADD_CATEGORY) {
                    onRestartCategoryPay();
                }
            }
        }
    }
}