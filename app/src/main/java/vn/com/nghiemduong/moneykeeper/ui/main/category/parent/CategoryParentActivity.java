package vn.com.nghiemduong.moneykeeper.ui.main.category.parent;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Objects;

import vn.com.nghiemduong.moneykeeper.R;
import vn.com.nghiemduong.moneykeeper.adapter.ParentCategoryAdapter;
import vn.com.nghiemduong.moneykeeper.data.db.category.CategoryDatabase;
import vn.com.nghiemduong.moneykeeper.data.model.db.Category;
import vn.com.nghiemduong.moneykeeper.ui.base.BaseActivity;
import vn.com.nghiemduong.moneykeeper.utils.AppConstants;
import vn.com.nghiemduong.moneykeeper.utils.AppUtils;

/**
 * - @created_by nxduong on 8/2/2021
 **/
public class CategoryParentActivity extends BaseActivity implements View.OnClickListener,
        ParentCategoryAdapter.IOnClickCategoryParentView {

    public final static int REQUEST_CODE_CATEGORY_PARENT = 6325;
    private RecyclerView rcvCategoryParent;
    private Toolbar tbCategoryParent;
    private RelativeLayout rlNotSelectCategoryParent;
    private CategoryDatabase mCategoryDatabase;
    private ParentCategoryAdapter mCategoryParentAdapter;
    private ImageView ivTickCategoryParent;
    private Category mParentCategory; // id của category parent đã được chọn
    private int mKey = -1; // Kiểm tra xem là hạng mục nào thu hay chi

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_parent);

        init();
    }

    /**
     * Khởi tạo / Ánh xạ view
     *
     * @created_by nxduong on 8/2/2021
     */
    private void init() {
        mKey = getIntent().getIntExtra("KEY_CATEGORY", -1);
        mParentCategory = (Category) Objects.requireNonNull(getIntent()
                .getBundleExtra("BUNDLE"))
                .getSerializable("BUNDLE_PARENT_CATEGORY");

        ivTickCategoryParent = findViewById(R.id.ivTickCategoryParent);

        if (mParentCategory == null) {
            ivTickCategoryParent.setVisibility(View.VISIBLE);
        }

        rlNotSelectCategoryParent = findViewById(R.id.rlNotSelectCategoryParent);
        rlNotSelectCategoryParent.setOnClickListener(this);

        tbCategoryParent = findViewById(R.id.tbCategoryParent);
        tbCategoryParent.setOnClickListener(this);

        rcvCategoryParent = findViewById(R.id.rcvCategoryParent);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rcvCategoryParent.setLayoutManager(layoutManager);

        mCategoryDatabase = new CategoryDatabase(this);

        mCategoryParentAdapter = new ParentCategoryAdapter(
                this, mCategoryDatabase.getAllParentCategory(mKey, AppConstants.CAP_DO_1),
                this, mParentCategory);
        rcvCategoryParent.setAdapter(mCategoryParentAdapter);
    }

    /**
     * Trả category đã được chọn về activity thêm hạng mục
     *
     * @param category hạng mục cha được chọn
     * @created_by nxduong on 8/2/2021
     */

    @Override
    public void onClickCategoryParent(Category category) {
        try {
            onFinishSelectCategoryParent(category);
        } catch (Exception e) {
            AppUtils.handlerException(e);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tbCategoryParent:
                onBackPressed();
                break;

            case R.id.rlNotSelectCategoryParent:
                onFinishSelectCategoryParent(null);
                break;
        }
    }

    /**
     * Hàm gửi dữ liệu category về activity trước
     *
     * @param category đối tượng hạng mục cha
     * @created_by nxduong on 8/2/2021
     */
    private void onFinishSelectCategoryParent(Category category) {
        try {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putSerializable("BUNDLE_PARENT_CATEGORY", category);
            intent.putExtra("BUNDLE", bundle);
            setResult(RESULT_OK, intent);
            finish();
        } catch (Exception e) {
            AppUtils.handlerException(e);
        }
    }
}