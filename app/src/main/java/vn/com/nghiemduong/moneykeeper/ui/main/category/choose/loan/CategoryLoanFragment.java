package vn.com.nghiemduong.moneykeeper.ui.main.category.choose.loan;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.com.nghiemduong.moneykeeper.R;
import vn.com.nghiemduong.moneykeeper.ui.base.BaseFragment;
import vn.com.nghiemduong.moneykeeper.ui.main.category.choose.ChooseCategoryActivity;

/**
 * -
 * Màn hình chọn hạng mục thu nợ
 * <p>
 * - @created_by nxduong on 27/1/2021
 **/
public class CategoryLoanFragment extends BaseFragment {
    private View mView;
    private RecyclerView rcvCategoryLoan;
    private ChooseCategoryActivity mChooseCategoriesActivity;

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

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mChooseCategoriesActivity = (ChooseCategoryActivity) context;
    }
}