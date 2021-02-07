package vn.com.nghiemduong.moneykeeper.ui.main.category.update.collect;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.com.nghiemduong.moneykeeper.R;
import vn.com.nghiemduong.moneykeeper.adapter.CategoryEditAdapter;
import vn.com.nghiemduong.moneykeeper.data.db.category.CategoryDatabase;
import vn.com.nghiemduong.moneykeeper.ui.base.BaseFragment;
import vn.com.nghiemduong.moneykeeper.utils.AppUtils;

/**
 * Màn hình sửa MỤC THU
 * - @created_by nxduong on 6/2/2021
 **/

public class EditCategoryCollectFragment extends BaseFragment {

    private RecyclerView rcvCategoryCollect;
    private View mView;
    private CategoryEditAdapter mCategoryEditAdapter;
    private CategoryDatabase mCategoryDatabase;

    public EditCategoryCollectFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_edit_category_collect, container, false);
        init();
        return mView;
    }

    /**
     * Khởi tạo ánh xạ view
     *
     * @created_by nxduong on 7/2/2021
     */
    private void init() {
        rcvCategoryCollect = mView.findViewById(R.id.rcvCategoryCollect);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rcvCategoryCollect.setLayoutManager(layoutManager);

        mCategoryDatabase = new CategoryDatabase(getContext());
        mCategoryEditAdapter = new CategoryEditAdapter(getContext(),
                mCategoryDatabase.getAllCategory(AppUtils.THU_TIEN, getContext()));
        rcvCategoryCollect.setAdapter(mCategoryEditAdapter);
    }
}