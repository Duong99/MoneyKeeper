package vn.com.nghiemduong.moneykeeper.ui.main.category.update.collect;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
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
import vn.com.nghiemduong.moneykeeper.ui.base.BaseFragment;
import vn.com.nghiemduong.moneykeeper.ui.main.category.update.add.AddCategoryActivity;
import vn.com.nghiemduong.moneykeeper.utils.AppUtils;

import static android.app.Activity.RESULT_OK;

/**
 * Màn hình sửa MỤC THU
 * - @created_by nxduong on 6/2/2021
 **/

public class EditCategoryCollectFragment extends BaseFragment implements View.OnClickListener {

    private RecyclerView rcvCategoryCollect;
    private View mView;
    private CategoryEditAdapter mCategoryEditAdapter;
    private CategoryDatabase mCategoryDatabase;
    private FloatingActionButton fabAddCategoryCollect;


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
        fabAddCategoryCollect = mView.findViewById(R.id.fabAddCategoryCollect);
        fabAddCategoryCollect.setOnClickListener(this);

        rcvCategoryCollect = mView.findViewById(R.id.rcvCategoryCollect);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rcvCategoryCollect.setLayoutManager(layoutManager);

        mCategoryDatabase = new CategoryDatabase(getContext());
        mCategoryEditAdapter = new CategoryEditAdapter(getContext(),
                mCategoryDatabase.getAllCategory(AppUtils.THU_TIEN, getContext()));
        rcvCategoryCollect.setAdapter(mCategoryEditAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fabAddCategoryCollect:
                Intent intent = new Intent(getContext(), AddCategoryActivity.class);
                intent.putExtra(AddCategoryActivity.VALUE_REQUEST,
                        AddCategoryActivity.REQUEST_CODE_KEY_CATEGORY_COLLECT);
                intent.putExtra(AddCategoryActivity.VALUE_STATUS, AddCategoryActivity.STATUS_ADD);
                startActivityForResult(intent, AddCategoryActivity.REQUEST_CODE_KEY_CATEGORY_COLLECT);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (data != null) {
                if (requestCode == AddCategoryActivity.REQUEST_CODE_KEY_CATEGORY_COLLECT) {

                }
            }
        }
    }
}