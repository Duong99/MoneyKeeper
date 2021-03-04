package vn.com.nghiemduong.moneykeeper.ui.main.category.icon;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

import vn.com.nghiemduong.moneykeeper.R;
import vn.com.nghiemduong.moneykeeper.adapter.IconCategoryAdapter;
import vn.com.nghiemduong.moneykeeper.data.model.db.Category;
import vn.com.nghiemduong.moneykeeper.ui.base.BaseActivity;
import vn.com.nghiemduong.moneykeeper.ui.main.category.update.add.AddCategoryActivity;
import vn.com.nghiemduong.moneykeeper.utils.AppUtils;

/**
 * -  Màn hình hiển thị danh sách biểu tượng icon category
 * - @created_by nxduong on 7/2/2021
 **/

public class IconCategoryActivity extends BaseActivity implements IconCategoryActivityMvpView,
        IconCategoryAdapter.IOnClickIconCategory {

    private RecyclerView rcvIconCategory;
    private IconCategoryActivityPresenter mIconCategoryActivityPresenter;
    private IconCategoryAdapter mIconCategoryAdapter;
    private Toolbar tbChooseIcon;
    private int mKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icon_category);

        init();

        tbChooseIcon.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    /**
     * Khởi tạo / ánh xạ view
     *
     * @created_by nxduong on 7/2/2021
     */
    private void init() {
        mKey = getIntent().getIntExtra("", -1);
        rcvIconCategory = findViewById(R.id.rcvIconCategory);
        tbChooseIcon = findViewById(R.id.tbChooseIcon);

        mIconCategoryActivityPresenter = new IconCategoryActivityPresenter(this);


    }

    @Override
    public void onFinishGetPathCategoryFromAsset(ArrayList<Category> listCategoriesPath) {
        GridLayoutManager linearLayoutManager =
                new GridLayoutManager(this, 4);
        linearLayoutManager.setInitialPrefetchItemCount(
                listCategoriesPath.size());
        rcvIconCategory.setLayoutManager(linearLayoutManager);

        mIconCategoryAdapter = new IconCategoryAdapter(this, listCategoriesPath, this);
        rcvIconCategory.setAdapter(mIconCategoryAdapter);
    }

    @Override
    public void onClickIcon(Category category) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("BUNDLE_ICON_CATEGORY", category);
        intent.putExtra("BUNDLE", bundle);
        setResult(RESULT_OK, intent);
        finish();
    }
}