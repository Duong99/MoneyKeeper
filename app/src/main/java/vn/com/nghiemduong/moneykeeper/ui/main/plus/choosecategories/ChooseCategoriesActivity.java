package vn.com.nghiemduong.moneykeeper.ui.main.plus.choosecategories;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;

import vn.com.nghiemduong.moneykeeper.R;
import vn.com.nghiemduong.moneykeeper.adapter.ViewPagerChooseCategoryAdapter;
import vn.com.nghiemduong.moneykeeper.data.model.Category;
import vn.com.nghiemduong.moneykeeper.ui.base.BaseActivity;

/**
 * -    Màn hình chọn hạn mục
 * - @created_by nxduong on 27/1/2021
 **/

public class ChooseCategoriesActivity extends BaseActivity {

    public final static int REQUEST_CODE_CHOOSE_CATEGORY = 912;
    private TabLayout tlChooseCategory;
    private ViewPager vpChooseCategory;
    private Toolbar tbChooseCategory;
    private ViewPagerChooseCategoryAdapter mChooseCategoryAdapter;

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
    }

    // Khởi tạo ánh xạ
    private void init() {
        tlChooseCategory = findViewById(R.id.tlChooseCategory);
        vpChooseCategory = findViewById(R.id.vpChooseCategory);
        tbChooseCategory = findViewById(R.id.tbChooseCategory);
        setSupportActionBar(tbChooseCategory);

        mChooseCategoryAdapter = new ViewPagerChooseCategoryAdapter(getSupportFragmentManager());

        tlChooseCategory.setupWithViewPager(vpChooseCategory);

        vpChooseCategory.addOnPageChangeListener(
                new TabLayout.TabLayoutOnPageChangeListener(tlChooseCategory));

        tlChooseCategory.setTabsFromPagerAdapter(mChooseCategoryAdapter);//deprecated

        tlChooseCategory.addOnTabSelectedListener(
                new TabLayout.ViewPagerOnTabSelectedListener(vpChooseCategory));
        vpChooseCategory.setAdapter(mChooseCategoryAdapter);

    }

    /**
     * Khi người dùng chọn một hạng mục hàm này sẽ được được
     * để trả dữ liệu về activity trước
     * - @created_by nxduong on 28/1/2021
     **/
    public void onFinishChooseCategory(Category category) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("BUNDLE_CATEGORY", category);
        intent.putExtra("BUNDLE", bundle);
        setResult(RESULT_OK, intent);
        finish();
    }
}