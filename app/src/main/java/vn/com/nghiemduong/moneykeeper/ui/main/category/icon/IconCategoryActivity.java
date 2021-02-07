package vn.com.nghiemduong.moneykeeper.ui.main.category.icon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

import vn.com.nghiemduong.moneykeeper.R;
import vn.com.nghiemduong.moneykeeper.adapter.IconCategoryAdapter;
import vn.com.nghiemduong.moneykeeper.data.model.Category;
import vn.com.nghiemduong.moneykeeper.ui.base.BaseActivity;
import vn.com.nghiemduong.moneykeeper.utils.AppUtils;

/**
 * -  Màn hình hiển thị danh sách biểu tượng icon category
 * <p>
 * - @created_by nxduong on 7/2/2021
 **/

public class IconCategoryActivity extends BaseActivity implements IconCategoryActivityMvpView {

    private RecyclerView rcvIconCategory;
    private IconCategoryActivityPresenter mIconCategoryActivityPresenter;
    private IconCategoryAdapter mIconCategoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icon_category);

        init();
    }

    /**
     * Khởi tạo / ánh xạ view
     *
     * @created_by nxduong on 7/2/2021
     */
    private void init() {
        rcvIconCategory = findViewById(R.id.rcvIconCategory);

        mIconCategoryActivityPresenter = new IconCategoryActivityPresenter(this);
        mIconCategoryActivityPresenter.doGetPathCategoryFromAssets(AppUtils.PATH_THU, this);
    }

    @Override
    public void onFinishGetPathCategoryFromAsset(ArrayList<Category> listCategoriesPath) {
        mIconCategoryAdapter = new IconCategoryAdapter(this, listCategoriesPath);
        rcvIconCategory.setAdapter(mIconCategoryAdapter);
    }
}