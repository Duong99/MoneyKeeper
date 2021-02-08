package vn.com.nghiemduong.moneykeeper.ui.main.category.update.add;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Objects;

import vn.com.nghiemduong.moneykeeper.R;
import vn.com.nghiemduong.moneykeeper.data.model.Category;
import vn.com.nghiemduong.moneykeeper.ui.base.BaseActivity;
import vn.com.nghiemduong.moneykeeper.ui.main.category.icon.IconCategoryActivity;
import vn.com.nghiemduong.moneykeeper.ui.main.category.parent.CategoryParentActivity;
import vn.com.nghiemduong.moneykeeper.utils.AppUtils;

/**
 * -
 * Màn hình cho phép thêm, sửa, xóa hạng mục (category)
 * <p>
 * - @created_by nxduong on 7/2/2021
 **/

public class AddCategoryActivity extends BaseActivity implements View.OnClickListener {

    public final static int REQUEST_CODE_KEY_CATEGORY_PAY = 154;
    public final static int REQUEST_CODE_KEY_CATEGORY_COLLECT = 153;

    public final static int REQUEST_CODE_ICON_CATEGORY_COLLECT = 14;

    public final static int STATUS_ADD = 42;
    public final static int STATUS_EDIT = 32;


    public final static String VALUE_REQUEST = "ValueRequestCategory";
    public final static String VALUE_STATUS = "ValueStatusCategory";


    private int mKey; // Key xác định xem là của mục thu tiền hay chi tiền
    private int mStatus; // trạng thái (status) xác định trạng thái người dùng muốn thêm, sửa hay xóa

    private ImageView ivIconCategoryAdd, ivIconCategoryParentAdd, ivCloseAddCategory,
            ivDoneAddCategory, ivCloseSelectedCategoryParent;
    private EditText etNameCategoryAdd, etExplainCategoryAdd;
    private LinearLayout llDeleteCategory, llSave, llSelectCategoryParent;
    private TextView tvTitleAddCategory, tvTitleIconParent;
    private RelativeLayout rlSelectedCategoryParent, rlSelectCategoryParent;
    private Category mCategoryParent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        mKey = getIntent().getIntExtra(VALUE_REQUEST, -1);
        mStatus = getIntent().getIntExtra(VALUE_STATUS, -1);
        init();

    }

    /**
     * Khởi tạo ánh xạ view
     *
     * @created_by nxduong on 7/2/2021
     */
    private void init() {
        ivIconCategoryAdd = findViewById(R.id.ivIconCategoryAdd);
        ivIconCategoryAdd.setOnClickListener(this);

        ivIconCategoryParentAdd = findViewById(R.id.ivIconCategoryParentAdd);

        ivCloseAddCategory = findViewById(R.id.ivCloseAddCategory);
        ivCloseAddCategory.setOnClickListener(this);

        ivDoneAddCategory = findViewById(R.id.ivDoneAddCategory);
        ivDoneAddCategory.setOnClickListener(this);

        llDeleteCategory = findViewById(R.id.llDelete);
        llDeleteCategory.setOnClickListener(this);

        llSave = findViewById(R.id.llSave);
        llSave.setOnClickListener(this);

        ivCloseSelectedCategoryParent = findViewById(R.id.ivCloseSelectedCategoryParent);
        ivCloseSelectedCategoryParent.setOnClickListener(this);

        rlSelectedCategoryParent = findViewById(R.id.rlSelectedCategoryParent);
        tvTitleIconParent = findViewById(R.id.tvTitleIconParent);

        rlSelectCategoryParent = findViewById(R.id.rlSelectCategoryParent);
        rlSelectCategoryParent.setOnClickListener(this);

        llSelectCategoryParent = findViewById(R.id.llSelectCategoryParent);
        llSelectCategoryParent.setOnClickListener(this);

        etNameCategoryAdd = findViewById(R.id.etNameCategoryAdd);
        etExplainCategoryAdd = findViewById(R.id.etExplain);
        tvTitleAddCategory = findViewById(R.id.tvTitleAddCategory);

        if (mStatus == STATUS_ADD) {
            llDeleteCategory.setVisibility(View.GONE);
            if (mKey == REQUEST_CODE_KEY_CATEGORY_PAY) {
                tvTitleAddCategory.setText(getString(R.string.add_pay_category));
            }

            if (mKey == REQUEST_CODE_KEY_CATEGORY_COLLECT) {
                tvTitleAddCategory.setText(getString(R.string.add_collect_category));
            }
        }

        if (mStatus == STATUS_EDIT) {
            llDeleteCategory.setVisibility(View.VISIBLE);

            if (mKey == REQUEST_CODE_KEY_CATEGORY_PAY) {
                tvTitleAddCategory.setText(getString(R.string.edit_pay_category));
            }

            if (mKey == REQUEST_CODE_KEY_CATEGORY_COLLECT) {
                tvTitleAddCategory.setText(getString(R.string.edit_collect_category));
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivIconCategoryAdd:
                try {
                    Intent intent = new Intent(this, IconCategoryActivity.class);
                    intent.putExtra(VALUE_REQUEST, mKey);
                    startActivityForResult(intent, REQUEST_CODE_ICON_CATEGORY_COLLECT);
                } catch (Exception e) {
                    AppUtils.handlerException(e);
                }
                break;

            case R.id.llDelete:
                break;

            case R.id.llSave:
                if (AppUtils.getEditText(etNameCategoryAdd).isEmpty()) {
                    etNameCategoryAdd.requestFocus();
                } else {

                }
                break;

            case R.id.ivDoneAddCategory:

                break;

            case R.id.ivCloseAddCategory:
                try {
                    onBackPressed();
                } catch (Exception e) {
                    AppUtils.handlerException(e);
                }
                break;

            case R.id.rlSelectCategoryParent:
            case R.id.llSelectCategoryParent:
                try {
                    Intent intentParent = new Intent(AddCategoryActivity.this,
                            CategoryParentActivity.class);
                    if (mCategoryParent == null) {
                        intentParent.putExtra("CATEGORY_PARENT_ID", -1);
                    } else {
                        intentParent.putExtra("CATEGORY_PARENT_ID",
                                mCategoryParent.getCategoryId());
                    }

                    startActivityForResult(intentParent,
                            CategoryParentActivity.REQUEST_CODE_CATEGORY_PARENT);
                } catch (Exception e) {
                    AppUtils.handlerException(e);
                }
                break;

            case R.id.ivCloseSelectedCategoryParent:
                onResetLayoutCategoryParent();
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (data != null) {
                if (requestCode == REQUEST_CODE_ICON_CATEGORY_COLLECT) {
                    Category category =
                            (Category) Objects.requireNonNull(data.getBundleExtra("BUNDLE"))
                                    .getSerializable("BUNDLE_ICON_CATEGORY");
                    if (category != null) {
                        ivIconCategoryAdd.setImageBitmap(
                                AppUtils.convertPathFileImageAssetsToBitmap(category.getCategoryPath(),
                                        this));
                    }
                }

                if (requestCode == CategoryParentActivity.REQUEST_CODE_CATEGORY_PARENT) {
                    mCategoryParent =
                            (Category) Objects.requireNonNull(data.getBundleExtra("BUNDLE"))
                                    .getSerializable("BUNDLE_CATEGORY_PARENT");
                    if (mCategoryParent != null) {
                        rlSelectedCategoryParent.setVisibility(View.VISIBLE);
                        rlSelectCategoryParent.setVisibility(View.GONE);
                        tvTitleIconParent.setText(mCategoryParent.getCategoryName());
                        ivIconCategoryParentAdd.setImageBitmap(
                                AppUtils.convertPathFileImageAssetsToBitmap(
                                        mCategoryParent.getCategoryPath(),
                                        this));
                    } else {
                        onResetLayoutCategoryParent();
                    }
                }
            }
        }
    }

    /**
     * Hàm có nhiệm vụ ẩn layout category parent hiện layout mặc định lên
     *
     * @created_by nxduong on 8/2/2021
     */

    private void onResetLayoutCategoryParent() {
        mCategoryParent = null;
        rlSelectCategoryParent.setVisibility(View.VISIBLE);
        rlSelectedCategoryParent.setVisibility(View.GONE);
        ivIconCategoryParentAdd.setImageBitmap(
                AppUtils.convertPathFileImageAssetsToBitmap(AppUtils.PATH_UN_KNOW, this));
    }
}