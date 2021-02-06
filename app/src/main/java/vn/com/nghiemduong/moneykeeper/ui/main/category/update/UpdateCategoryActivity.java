package vn.com.nghiemduong.moneykeeper.ui.main.category.update;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import vn.com.nghiemduong.moneykeeper.R;
import vn.com.nghiemduong.moneykeeper.adapter.ViewPagerEditCategoryAdapter;
import vn.com.nghiemduong.moneykeeper.ui.base.BaseActivity;

/**
 * -  MÀn hình sửa hạng mục chính chứa 2 màn hình fragment MỤC CHI VÀ MỤC THU
 * - @created_by nxduong on 6/2/2021
 **/

public class UpdateCategoryActivity extends BaseActivity {

    private Toolbar tbEditCategory;
    private TabLayout tlEditCategory;
    private ViewPager vpEditCategory;
    private ViewPagerEditCategoryAdapter mViewPagerEditCategoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_category);

        init();

        tbEditCategory.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    /**
     * Khởi tạo / ánh xạ view
     *
     * @created_by nxduong on 6/2/2021
     */
    private void init() {
        tbEditCategory = findViewById(R.id.tbEditCategory);
        tlEditCategory = findViewById(R.id.tlEditCategory);
        vpEditCategory = findViewById(R.id.vpEditCategory);

        mViewPagerEditCategoryAdapter = new ViewPagerEditCategoryAdapter(getSupportFragmentManager());
        tlEditCategory.setupWithViewPager(vpEditCategory);
        vpEditCategory.addOnPageChangeListener(
                new TabLayout.TabLayoutOnPageChangeListener(tlEditCategory));
        tlEditCategory.addOnTabSelectedListener(
                new TabLayout.ViewPagerOnTabSelectedListener(vpEditCategory));
        vpEditCategory.setAdapter(mViewPagerEditCategoryAdapter);
    }
}