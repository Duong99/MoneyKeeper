package vn.com.nghiemduong.moneykeeper.ui.main.category.update.add;

import androidx.annotation.Nullable;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Objects;

import vn.com.nghiemduong.moneykeeper.R;
import vn.com.nghiemduong.moneykeeper.data.db.category.CategoryDatabase;
import vn.com.nghiemduong.moneykeeper.data.db.category.SubCategoryDatabase;
import vn.com.nghiemduong.moneykeeper.data.model.Category;
import vn.com.nghiemduong.moneykeeper.data.model.SubCategory;
import vn.com.nghiemduong.moneykeeper.ui.base.BaseActivity;
import vn.com.nghiemduong.moneykeeper.ui.dialog.attention.AttentionDeleteDialog;
import vn.com.nghiemduong.moneykeeper.ui.main.category.icon.IconCategoryActivity;
import vn.com.nghiemduong.moneykeeper.ui.main.category.parent.CategoryParentActivity;
import vn.com.nghiemduong.moneykeeper.utils.AppUtils;
import vn.com.nghiemduong.moneykeeper.utils.DBUtils;

/**
 * Màn hình cho phép thêm, sửa, xóa hạng mục (category)
 * <p>
 * - @created_by nxduong on 7/2/2021
 **/

public class AddCategoryActivity extends BaseActivity implements View.OnClickListener,
        AddCategoryActivityMvpView, AttentionDeleteDialog.IOnClickAttentionDialog {

    public final static int REQUEST_CODE_KEY_CATEGORY_PAY = 154;
    public final static int REQUEST_CODE_KEY_CATEGORY_COLLECT = 153;
    public final static int REQUEST_CODE_ICON_CATEGORY_COLLECT = 14;
    public final static int STATUS_ADD = 42;
    public final static int STATUS_EDIT = 32;
    public final static int STATUS_UPDATE_FINISH = 99;
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
    private SubCategory mSubCategory;
    private String mCategoryPath = AppUtils.PATH_UN_KNOW; // Đường dẫn mặc định của ảnh hạng mục
    private int mType; // Kiểu hạng mục thu/chi
    private CategoryDatabase mCategoryDatabase;
    private SubCategoryDatabase mSubCategoryDatabase;
    private AddCategoryActivityPresenter mAddCategoryActivityPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        mKey = getIntent().getIntExtra(VALUE_REQUEST, -1);

        init();
    }

    /**
     * Khởi tạo ánh xạ view
     *
     * @created_by nxduong on 7/2/2021
     */
    private void init() {
        ivIconCategoryAdd = findViewById(R.id.ivIconCategoryAdd);
        ivIconCategoryAdd.setImageBitmap(
                AppUtils.convertPathFileImageAssetsToBitmap(mCategoryPath, this));
        ivIconCategoryAdd.setOnClickListener(this);

        ivIconCategoryParentAdd = findViewById(R.id.ivIconCategoryParentAdd);
        ivIconCategoryParentAdd.setImageBitmap(
                AppUtils.convertPathFileImageAssetsToBitmap(mCategoryPath, this));

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

        mCategoryDatabase = new CategoryDatabase(this);
        mSubCategoryDatabase = new SubCategoryDatabase(this);
        mAddCategoryActivityPresenter = new AddCategoryActivityPresenter(this,
                this);
        mAddCategoryActivityPresenter.doGetStatusUpdateCategory();
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
                try {
                    new AttentionDeleteDialog(this, this,
                            AttentionDeleteDialog.ATTENTION_DELETE_CATEGORY).show();
                } catch (Exception e) {
                    AppUtils.handlerException(e);
                }
                break;

            case R.id.ivDoneAddCategory:
            case R.id.llSave:
                if (AppUtils.getEditText(etNameCategoryAdd).isEmpty()) {
                    etNameCategoryAdd.requestFocus();
                } else if (mCategoryPath == null) {

                } else {
                    if (mStatus == STATUS_ADD) {
                        try {
                            insertCategory();
                        } catch (Exception e) {
                            AppUtils.handlerException(e);
                        }
                    }

                    if (mStatus == STATUS_EDIT) {
                        try {
                            updateCategory();
                        } catch (Exception e) {
                            AppUtils.handlerException(e);
                        }
                    }
                }
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
                    intentParent.putExtra(VALUE_REQUEST, mKey);
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

    private void updateCategory() {
        if (mSubCategory == null) { // Sửa hạng mục cha
            if (mCategoryParent != null) {
                Category categoryParent = new Category(mCategoryParent.getCategoryId(),
                        AppUtils.getEditText(etNameCategoryAdd), mCategoryPath,
                        AppUtils.getEditText(etExplainCategoryAdd), mType);
                long update = mCategoryDatabase.updateCategory(categoryParent);
                if (update == DBUtils.checkDBFail) {
                    showToast(getString(R.string.update_category_fail));
                } else {
                    showToast(getString(R.string.update_category_success));
                    onFinishActivityResult();
                }
            }
        } else { // Sửa hạng mục con
            if (mCategoryParent == null) { // Sửa hạng mục con thành hạng mục cha
                // Xóa hạng mục con ở hạng mục cha trước
                long delete = mSubCategoryDatabase
                        .deleteSubCategory(mSubCategory.getSubCategoryId());

                if (delete == DBUtils.checkDBFail) {
                    showToast(getString(R.string.update_category_fail));
                } else {
                    // thêm hạng mục cha mới
                    Category categoryParent = new Category(AppUtils.getEditText(etNameCategoryAdd),
                            mCategoryPath, AppUtils.getEditText(etExplainCategoryAdd),
                            mType);
                    long insert = mCategoryDatabase.insertCategory(categoryParent);
                    if (insert == DBUtils.checkDBFail) {
                        showToast(getString(R.string.update_category_fail));
                    } else {
                        showToast(getString(R.string.update_category_success));
                        onFinishActivityResult();
                    }
                }
            } else { // Sửa hạng mục con, sửa cả hạng mục cha
                SubCategory subCategory = new SubCategory(mSubCategory.getSubCategoryId(),
                        mCategoryParent.getCategoryId(),
                        AppUtils.getEditText(etNameCategoryAdd),
                        mCategoryPath,
                        AppUtils.getEditText(etExplainCategoryAdd));
                long update = mSubCategoryDatabase.updateSubCategory(subCategory);
                if (update == DBUtils.checkDBFail) {
                    showToast(getString(R.string.update_sub_category_fail));
                } else {
                    showToast(getString(R.string.update_sub_category_success));
                    onFinishActivityResult();
                }
            }
        }
    }

    /**
     * Hàm thêm hạng mục
     *
     * @created_by nxduong on 9/2/2021
     */
    private void insertCategory() {
        if (mCategoryParent == null) { // Thêm hạng mục cha
            Category categoryParent = new Category(AppUtils.getEditText(etNameCategoryAdd),
                    mCategoryPath, AppUtils.getEditText(etExplainCategoryAdd), mType);
            long insert = mCategoryDatabase.insertCategory(categoryParent);
            if (insert == DBUtils.checkDBFail) {
                showToast(getString(R.string.insert_category_fail));
            } else {
                showToast(getString(R.string.insert_category_success));
                onFinishActivityResult();
            }
        } else { // Thêm hạng mục con
            SubCategory subCategory = new SubCategory(mCategoryParent.getCategoryId(),
                    AppUtils.getEditText(etNameCategoryAdd), mCategoryPath,
                    AppUtils.getEditText(etExplainCategoryAdd));
            long insert = mSubCategoryDatabase.insertSubCategory(subCategory);
            if (insert == DBUtils.checkDBFail) {
                showToast(getString(R.string.insert_sub_category_fail));
            } else {
                showToast(getString(R.string.insert_sub_category_success));
                onFinishActivityResult();
            }
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
                        mCategoryPath = category.getCategoryPath();
                        ivIconCategoryAdd.setImageBitmap(
                                AppUtils.convertPathFileImageAssetsToBitmap(mCategoryPath,
                                        this));
                    }
                }

                if (requestCode == CategoryParentActivity.REQUEST_CODE_CATEGORY_PARENT) {
                    mCategoryParent =
                            (Category) Objects.requireNonNull(data.getBundleExtra("BUNDLE"))
                                    .getSerializable("BUNDLE_CATEGORY_PARENT");
                    if (mCategoryParent != null) {
                        setValueLayoutParentCategory();
                    } else {
                        onResetLayoutCategoryParent();
                    }
                }
            }
        }
    }

    /**
     * Set giá trị view trong layout chứa hạng mục cha khi hạng mục cha !=null
     *
     * @created_by nxduong on 9/2/2021
     */
    private void setValueLayoutParentCategory() {
        rlSelectedCategoryParent.setVisibility(View.VISIBLE);
        rlSelectCategoryParent.setVisibility(View.GONE);
        tvTitleIconParent.setText(mCategoryParent.getCategoryName());
        ivIconCategoryParentAdd.setImageBitmap(
                AppUtils.convertPathFileImageAssetsToBitmap(
                        mCategoryParent.getCategoryPath(),
                        this));
    }

    /**
     * Hàm có nhiệm vụ ẩn layout category parent hiện layout chọn hạng mục cha mặc định lên
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

    @Override
    public void resultGetBundleParentCategory(Category parentCategory) {
        this.mCategoryParent = parentCategory;
        if (parentCategory != null) {
            this.mCategoryPath = mCategoryParent.getCategoryPath();
            ivIconCategoryAdd.setImageBitmap(AppUtils.convertPathFileImageAssetsToBitmap(
                    mCategoryPath, this));
            etExplainCategoryAdd.setText(mCategoryParent.getExplain());
            etNameCategoryAdd.setText(mCategoryParent.getCategoryName());
        }
    }

    @Override
    public void resultGetBundleSubCategory(SubCategory subCategory) {
        this.mSubCategory = subCategory;
        if (mSubCategory != null) {
            if (mCategoryParent == null) {
                mCategoryParent = mCategoryDatabase.getCategory(mSubCategory.getCategoryId());
                setValueLayoutParentCategory();

                this.mCategoryPath = mSubCategory.getSubCategoryPath();
                ivIconCategoryAdd.setImageBitmap(AppUtils.convertPathFileImageAssetsToBitmap(
                        mCategoryPath, this));
                etExplainCategoryAdd.setText(mSubCategory.getExplain());
                etNameCategoryAdd.setText(mSubCategory.getSubCategoryName());
            }
        } else {
            // Ẩn layout chọn hạng mục cha khi người dùng click vào hạng mục cha
            llSelectCategoryParent.setVisibility(View.GONE);
        }
    }

    @Override
    public void resultGetStatusCategory(int status) {
        this.mStatus = status;
        if (mStatus == STATUS_ADD) {
            llDeleteCategory.setVisibility(View.GONE);
            if (mKey == REQUEST_CODE_KEY_CATEGORY_PAY) {
                tvTitleAddCategory.setText(getString(R.string.add_pay_category));
                mType = AppUtils.CHI_TIEN;
            }

            if (mKey == REQUEST_CODE_KEY_CATEGORY_COLLECT) {
                tvTitleAddCategory.setText(getString(R.string.add_collect_category));
                mType = AppUtils.THU_TIEN;
            }
        }

        if (mStatus == STATUS_EDIT) {
            llDeleteCategory.setVisibility(View.VISIBLE);
            mAddCategoryActivityPresenter.doGetBundleParentCategory();
            mAddCategoryActivityPresenter.doGetBundleSubCategory();

            if (mKey == REQUEST_CODE_KEY_CATEGORY_PAY) {
                tvTitleAddCategory.setText(getString(R.string.edit_pay_category));
                mType = AppUtils.CHI_TIEN;
            }

            if (mKey == REQUEST_CODE_KEY_CATEGORY_COLLECT) {
                tvTitleAddCategory.setText(getString(R.string.edit_collect_category));
                mType = AppUtils.THU_TIEN;
            }
        }
    }

    @Override
    public void onClickYesDelete() {
        if (mCategoryParent != null) {
            long delete;
            if (mSubCategory == null) { // xóa hạng mục cha
                delete = mCategoryDatabase.deleteCategory(mCategoryParent.getCategoryId());
            } else { // Xóa hạng mục con
                delete = mSubCategoryDatabase.deleteSubCategory(mSubCategory.getSubCategoryId());
            }

            if (delete == DBUtils.checkDBFail) {
                showToast(getString(R.string.delete_category_fail));
            } else {
                showToast(getString(R.string.delete_category_success));
                onFinishActivityResult();
            }
        }
    }

    /**
     * Hàm hoàn thành khi thêm, sửa, xóa hạng mục thành công
     *
     * @created_by nxduong on 9/2/2021
     */

    private void onFinishActivityResult() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("BUNDLE_PARENT_CATEGORY", mCategoryParent);
        bundle.putSerializable("BUNDLE_SUB_CATEGORY", mSubCategory);
        intent.putExtra(VALUE_STATUS, STATUS_UPDATE_FINISH);
        intent.putExtra("BUNDLE", bundle);
        setResult(RESULT_OK, intent);
        finish();
    }
}