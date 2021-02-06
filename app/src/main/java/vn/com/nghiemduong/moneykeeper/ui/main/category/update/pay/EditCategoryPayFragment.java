package vn.com.nghiemduong.moneykeeper.ui.main.category.update.pay;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.com.nghiemduong.moneykeeper.R;

/**
 * Màn hình sửa MỤC CHI
 * - @created_by nxduong on 6/2/2021
 **/

public class EditCategoryPayFragment extends Fragment {

    public EditCategoryPayFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_category_pay, container, false);
    }
}