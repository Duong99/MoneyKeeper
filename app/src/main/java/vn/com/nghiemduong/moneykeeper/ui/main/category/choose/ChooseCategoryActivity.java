package vn.com.nghiemduong.moneykeeper.ui.main.category.choose;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

import vn.com.nghiemduong.moneykeeper.R;
import vn.com.nghiemduong.moneykeeper.adapter.ViewPagerChooseCategoryAdapter;
import vn.com.nghiemduong.moneykeeper.data.db.category.CategoryDatabase;
import vn.com.nghiemduong.moneykeeper.data.model.db.Category;
import vn.com.nghiemduong.moneykeeper.ui.base.BaseActivity;
import vn.com.nghiemduong.moneykeeper.ui.main.category.update.UpdateCategoryActivity;
import vn.com.nghiemduong.moneykeeper.utils.AppUtils;

/**
 * -    Màn hình chọn hạng mục
 * - @created_by nxduong on 27/1/2021
 **/

public class ChooseCategoryActivity extends BaseActivity {

    public final static int REQUEST_CODE_CHOOSE_CATEGORY = 111;
    private TabLayout tlChooseCategory;
    private ViewPager vpChooseCategory;
    private Toolbar tbChooseCategory;
    private ViewPagerChooseCategoryAdapter mChooseCategoryAdapter;
    private CategoryDatabase mCategoryDatabase;
    private Category mCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_category);

        init();

        tbChooseCategory.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        tbChooseCategory.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_edit_category:
                        try {
                            Intent intent = new Intent(ChooseCategoryActivity.this,
                                    UpdateCategoryActivity.class);
                            startActivity(intent);

                        } catch (Exception e) {
                            AppUtils.handlerException(e);
                        }
                        break;

                    case R.id.item_setting_category:
                        showToast(getString(R.string.evolving_functions));
                        break;
                }
                return false;
            }
        });
    }

    // Khởi tạo ánh xạ
    private void init() {
        mCategory = (Category) Objects.requireNonNull(getIntent().getBundleExtra("BUNDLE"))
                .getSerializable("BUNDLE_CATEGORY");

        tlChooseCategory = findViewById(R.id.tlChooseCategory);
        vpChooseCategory = findViewById(R.id.vpChooseCategory);
        tbChooseCategory = findViewById(R.id.tbChooseCategory);

        setSupportActionBar(tbChooseCategory);

        mChooseCategoryAdapter = new ViewPagerChooseCategoryAdapter(getSupportFragmentManager());

        tlChooseCategory.setupWithViewPager(vpChooseCategory);

        vpChooseCategory.addOnPageChangeListener(
                new TabLayout.TabLayoutOnPageChangeListener(tlChooseCategory));

        //tlChooseCategory.setTabsFromPagerAdapter(mChooseCategoryAdapter);//deprecated

        tlChooseCategory.addOnTabSelectedListener(
                new TabLayout.ViewPagerOnTabSelectedListener(vpChooseCategory));
        vpChooseCategory.setAdapter(mChooseCategoryAdapter);

        if (mCategory != null) {
            Objects.requireNonNull(tlChooseCategory.getTabAt(mCategory.getType() - 1)).select();
        }

        mCategoryDatabase = new CategoryDatabase(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_filter_category, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void onFinishChooseCategory(Category category) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("BUNDLE_CATEGORY", category);
        intent.putExtra("BUNDLE", bundle);
        setResult(RESULT_OK, intent);
        finish();
    }

    public Category getCategory() {
        return mCategory;
    }
}