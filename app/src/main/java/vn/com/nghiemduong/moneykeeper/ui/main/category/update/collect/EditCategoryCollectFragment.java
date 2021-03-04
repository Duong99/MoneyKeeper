package vn.com.nghiemduong.moneykeeper.ui.main.category.update.collect;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.com.nghiemduong.moneykeeper.R;
import vn.com.nghiemduong.moneykeeper.ui.base.BaseFragment;

/**
 * Màn hình sửa MỤC THU
 * - @created_by nxduong on 6/2/2021
 **/

public class EditCategoryCollectFragment extends BaseFragment {

    private View mView;

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

    }
}