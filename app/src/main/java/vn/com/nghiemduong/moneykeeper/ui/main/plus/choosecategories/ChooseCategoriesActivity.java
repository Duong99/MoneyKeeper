package vn.com.nghiemduong.moneykeeper.ui.main.plus.choosecategories;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import vn.com.nghiemduong.moneykeeper.R;
import vn.com.nghiemduong.moneykeeper.adapter.ViewPagerChooseCategoryAdapter;

/**
 * -    Màn hình chọn hạn mục
 * - @created_by nxduong on 27/1/2021
 **/

public class ChooseCategoriesActivity extends AppCompatActivity {

    private TabLayout tlChooseCategory;
    private ViewPager vpChooseCategory;
    private Toolbar tbChooseCategory;
    private ViewPagerChooseCategoryAdapter mChooseCategoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_category);

        init();
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
}