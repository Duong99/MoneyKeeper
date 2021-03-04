package vn.com.nghiemduong.moneykeeper.ui.main.category.update.add;

import android.os.Bundle;

import vn.com.nghiemduong.moneykeeper.R;

import vn.com.nghiemduong.moneykeeper.ui.base.BaseActivity;

/**
 * Màn hình cho phép thêm, sửa, xóa hạng mục (category)
 * <p>
 * - @created_by nxduong on 7/2/2021
 **/

public class AddCategoryActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        init();
    }

    /**
     * Khởi tạo ánh xạ view
     *
     * @created_by nxduong on 7/2/2021
     */
    private void init() {

    }
}