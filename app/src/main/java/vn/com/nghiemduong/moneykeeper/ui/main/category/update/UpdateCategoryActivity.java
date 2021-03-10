package vn.com.nghiemduong.moneykeeper.ui.main.category.update;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import vn.com.nghiemduong.moneykeeper.R;
import vn.com.nghiemduong.moneykeeper.adapter.ViewPagerEditCategoryAdapter;
import vn.com.nghiemduong.moneykeeper.data.model.db.Category;
import vn.com.nghiemduong.moneykeeper.ui.base.BaseActivity;
import vn.com.nghiemduong.moneykeeper.ui.main.category.update.add.AddCategoryActivity;
import vn.com.nghiemduong.moneykeeper.utils.AppConstants;
import vn.com.nghiemduong.moneykeeper.utils.AppUtils;

/**
 * -  MÀn hình sửa hạng mục chính chứa 2 màn hình fragment MỤC CHI VÀ MỤC THU
 * - @created_by nxduong on 6/2/2021
 **/

public class UpdateCategoryActivity extends BaseActivity implements View.OnClickListener {

    private Toolbar tbEditCategory;
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
        TabLayout tlEditCategory = findViewById(R.id.tlEditCategory);
        vpEditCategory = findViewById(R.id.vpEditCategory);

        FloatingActionButton fabAddCategory = findViewById(R.id.fabAddCategory);
        fabAddCategory.setOnClickListener(this);

        mViewPagerEditCategoryAdapter = new ViewPagerEditCategoryAdapter(getSupportFragmentManager());
        tlEditCategory.setupWithViewPager(vpEditCategory);
        vpEditCategory.addOnPageChangeListener(
                new TabLayout.TabLayoutOnPageChangeListener(tlEditCategory));
        tlEditCategory.addOnTabSelectedListener(
                new TabLayout.ViewPagerOnTabSelectedListener(vpEditCategory));
        vpEditCategory.setAdapter(mViewPagerEditCategoryAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fabAddCategory:
                try {
                    Intent intentAddCategory = new Intent(this, AddCategoryActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("BUNDLE_CATEGORY", null);
                    intentAddCategory.putExtra("BUNDLE", bundle);
                    intentAddCategory.putExtra("KEY_CATEGORY", (vpEditCategory.getCurrentItem() + 1));
                    startActivityForResult(intentAddCategory, AddCategoryActivity.REQUEST_CODE_ADD_CATEGORY);
                } catch (Exception e) {
                    AppUtils.handlerException(e);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (data != null) {
                if (requestCode == AddCategoryActivity.REQUEST_CODE_ADD_CATEGORY) {
                    int mTypeKey = data.getIntExtra("KEY_CATEGORY", -1);
                    if (mTypeKey == AppConstants.THU_TIEN) {
                        mIOnGetRestartCategoryCollect.onRestartCategoryCollect();
                    }
                    if (mTypeKey == AppConstants.CHI_TIEN) {
                        mOnGetRestartCategoryPay.onRestartCategoryPay();
                    }
                }
            }
        }
    }

    private IOnGetRestartCategoryPay mOnGetRestartCategoryPay;
    private IOnGetRestartCategoryCollect mIOnGetRestartCategoryCollect;

    public void setOnGetRestartCategoryPay(IOnGetRestartCategoryPay onGetRestartCategoryPay) {
        this.mOnGetRestartCategoryPay = onGetRestartCategoryPay;
    }

    public void setOnGetRestartCategoryCollect(IOnGetRestartCategoryCollect onGetRestartCategoryCollect) {
        this.mIOnGetRestartCategoryCollect = onGetRestartCategoryCollect;
    }

    public interface IOnGetRestartCategoryPay {
        void onRestartCategoryPay();
    }

    public interface IOnGetRestartCategoryCollect {
        void onRestartCategoryCollect();
    }
}