package vn.com.nghiemduong.moneykeeper.ui.main.category.update.pay;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import vn.com.nghiemduong.moneykeeper.R;
import vn.com.nghiemduong.moneykeeper.adapter.CategoryEditAdapter;
import vn.com.nghiemduong.moneykeeper.data.db.category.CategoryDatabase;
import vn.com.nghiemduong.moneykeeper.ui.main.category.update.add.AddCategoryActivity;
import vn.com.nghiemduong.moneykeeper.utils.AppUtils;

/**
 * Màn hình sửa MỤC CHI
 * - @created_by nxduong on 6/2/2021
 **/

public class EditCategoryPayFragment extends Fragment implements View.OnClickListener {

    private RecyclerView rcvCategoryCollect;
    private View mView;
    private CategoryEditAdapter mCategoryEditAdapter;
    private CategoryDatabase mCategoryDatabase;
    private FloatingActionButton fabAddCategoryPay;

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

    /**
     * HÀm khởi tạo ánh xạ view
     *
     * @created_by nxduong on 7/2/2021
     */
    private void init() {
        fabAddCategoryPay = mView.findViewById(R.id.fabAddCategoryPay);
        fabAddCategoryPay.setOnClickListener(this);

        rcvCategoryCollect = mView.findViewById(R.id.rcvCategoryCollect);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rcvCategoryCollect.setLayoutManager(layoutManager);

        mCategoryDatabase = new CategoryDatabase(getContext());
        mCategoryEditAdapter = new CategoryEditAdapter(getContext(),
                mCategoryDatabase.getAllCategory(AppUtils.CHI_TIEN, getContext()));
        rcvCategoryCollect.setAdapter(mCategoryEditAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fabAddCategoryPay:
                Intent intent = new Intent(getContext(), AddCategoryActivity.class);
                startActivity(intent);
                break;
        }
    }
}