package vn.com.nghiemduong.moneykeeper.ui.main.category.update.add;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import vn.com.nghiemduong.moneykeeper.R;

import vn.com.nghiemduong.moneykeeper.data.db.category.CategoryDatabase;
import vn.com.nghiemduong.moneykeeper.data.model.db.Category;
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

    public final static int REQUEST_CODE_ADD_CATEGORY = 532;
    private ImageView ivIconCategoryAdd, ivIconCategoryParentAdd;
    private int mKeyType; // key kiểm tra xem là đang ở thể loại thu tiền hay chi tiền
    private String mPathCategory = AppUtils.PATH_UN_KNOW;
    private AddCategoryActivityPresenter mAddCategoryActivityPresenter;
    private Category mParentCategory; // Hạng mục cha
    private Category mCategory; //hạng mục
    private RelativeLayout rlSelectedCategoryParent, rlSelectCategoryParent;
    private TextView tvTitleParentCategory;
    private EditText etNameCategoryAdd, etExplain;
    private LinearLayout llDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        init();
    }

    /**
     * Khởi tạo ánh xạ view
     *
     * @created_by nxduong on 7/2/2021
     */
    private void init() {
        mKeyType = getIntent().getIntExtra("KEY_CATEGORY", -1);

        ivIconCategoryAdd = findViewById(R.id.ivIconCategoryAdd);
        etNameCategoryAdd = findViewById(R.id.etNameCategoryAdd);
        etExplain = findViewById(R.id.etExplain);
        llDelete = findViewById(R.id.llDelete);

        llDelete.setOnClickListener(this);

        LinearLayout llChooseIconCategory = findViewById(R.id.llChooseIconCategory);
        llChooseIconCategory.setOnClickListener(this);

        ImageView ivDoneAddCategory = findViewById(R.id.ivDoneAddCategory);
        ivDoneAddCategory.setOnClickListener(this);

        // Hạng mục cha
        LinearLayout llSelectCategoryParent = findViewById(R.id.llSelectCategoryParent);
        llSelectCategoryParent.setOnClickListener(this);
        ivIconCategoryParentAdd = findViewById(R.id.ivIconCategoryParentAdd);
        tvTitleParentCategory = findViewById(R.id.tvTitleParentCategory);
        rlSelectCategoryParent = findViewById(R.id.rlSelectCategoryParent);

        rlSelectedCategoryParent = findViewById(R.id.rlSelectedCategoryParent);
        ImageView ivCloseSelectedCategoryParent = findViewById(R.id.ivCloseSelectedCategoryParent);
        ivCloseSelectedCategoryParent.setOnClickListener(this);

        ImageView ivCloseAddCategory = findViewById(R.id.ivCloseAddCategory);
        ivCloseAddCategory.setOnClickListener(this);

        LinearLayout llSave = findViewById(R.id.llSave);
        llSave.setOnClickListener(this);

        mAddCategoryActivityPresenter = new AddCategoryActivityPresenter(this, this);
        mAddCategoryActivityPresenter.getCategoryFromBundle();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llChooseIconCategory: // Chọn ảnh cho icon hạng mục
                try {
                    Intent intentIcon = new Intent(this, IconCategoryActivity.class);
                    intentIcon.putExtra("KEY_CATEGORY", mKeyType);
                    startActivityForResult(intentIcon, IconCategoryActivity.REQUEST_CODE_CHOOSE_ICON_CATEGORY);
                } catch (Exception e) {
                    AppUtils.handlerException(e);
                }
                break;

            case R.id.llSelectCategoryParent: // Chọn hạng mục cha
                try {
                    Intent intentParentCategory = new Intent(this, CategoryParentActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("BUNDLE_PARENT_CATEGORY", mParentCategory);
                    intentParentCategory.putExtra("KEY_CATEGORY", mKeyType);
                    intentParentCategory.putExtra("BUNDLE", bundle);
                    startActivityForResult(intentParentCategory, CategoryParentActivity.REQUEST_CODE_CATEGORY_PARENT);

                } catch (Exception e) {
                    AppUtils.handlerException(e);
                }
                break;

            case R.id.ivCloseAddCategory: // Đóng / thoát thêm hạng mục
                try {
                    onBackPressed();
                } catch (Exception e) {
                    AppUtils.handlerException(e);
                }
                break;

            case R.id.ivCloseSelectedCategoryParent: // Xóa hạng mục cha
                try {
                    mParentCategory = null;
                    rlSelectCategoryParent.setVisibility(View.VISIBLE);
                    rlSelectedCategoryParent.setVisibility(View.GONE);
                    ivIconCategoryParentAdd.setImageBitmap(
                            AppUtils.convertPathFileImageAssetsToBitmap(AppUtils.PATH_UN_KNOW, this));
                } catch (Exception e) {
                    AppUtils.handlerException(e);
                }
                break;

            case R.id.ivDoneAddCategory:
            case R.id.llSave:
                if (AppUtils.getEditText(etNameCategoryAdd).isEmpty()) {

                } else {
                    if (mParentCategory == null) { // Thêm hạng mục cha
                        Category category = new Category(AppUtils.getEditText(etNameCategoryAdd), mPathCategory,
                                AppUtils.getEditText(etExplain), mKeyType, 0, AppUtils.CAP_DO_1);
                        mAddCategoryActivityPresenter.insertCategory(category, this);
                    } else { // Thêm hạng mục con
                        Category category = new Category(AppUtils.getEditText(etNameCategoryAdd), mPathCategory,
                                AppUtils.getEditText(etExplain), mKeyType,
                                mParentCategory.getCategoryId(), AppUtils.CAP_DO_2);
                        mAddCategoryActivityPresenter.insertCategory(category, this);
                    }
                }
                break;

            case R.id.llDelete: // Xóa hạng mục
                try {
                    new AttentionDeleteDialog(this, this,
                            AttentionDeleteDialog.ATTENTION_DELETE_CATEGORY).show();
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
                if (requestCode == IconCategoryActivity.REQUEST_CODE_CHOOSE_ICON_CATEGORY) {
                    mAddCategoryActivityPresenter.getCategoryPathWhenChooseIcon(data);
                }

                if (requestCode == CategoryParentActivity.REQUEST_CODE_CATEGORY_PARENT) {
                    mAddCategoryActivityPresenter.getParentCategory(data);
                }
            }
        }
    }

    @Override
    public void resultGetCategoryPathWhenChooseIcon(String pathCategory) {
        this.mPathCategory = pathCategory;
        if (pathCategory != null) {
            ivIconCategoryAdd.setImageBitmap(
                    AppUtils.convertPathFileImageAssetsToBitmap(mPathCategory, this));
        }
    }

    @Override
    public void resultGetParentCategory(Category parentCategory) {
        this.mParentCategory = parentCategory;
        if (this.mParentCategory != null) {
            rlSelectCategoryParent.setVisibility(View.GONE);
            rlSelectedCategoryParent.setVisibility(View.VISIBLE);

            ivIconCategoryParentAdd.setImageBitmap(
                    AppUtils.convertPathFileImageAssetsToBitmap(
                            mParentCategory.getCategoryPath(), this));
            tvTitleParentCategory.setText(mParentCategory.getCategoryName());
        } else {
            rlSelectCategoryParent.setVisibility(View.VISIBLE);
            rlSelectedCategoryParent.setVisibility(View.GONE);
            ivIconCategoryParentAdd.setImageBitmap(
                    AppUtils.convertPathFileImageAssetsToBitmap(AppUtils.PATH_UN_KNOW, this));
        }
    }

    @Override
    public void insertCategorySuccess() {
        showCustomToast(getString(R.string.insert_category_success), AppUtils.TOAST_SUCCESS);
        Intent intent = new Intent();
        intent.putExtra("KEY_CATEGORY", mKeyType);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void insertCategoryFail() {
        showCustomToast(getString(R.string.insert_category_fail), AppUtils.TOAST_ERROR);
        onBackPressed();
    }

    @Override
    public void resultGetCategoryFromBundle(Category category) {
        this.mCategory = category;
        if (this.mCategory != null) {
            etNameCategoryAdd.setText(mCategory.getCategoryName());
            ivIconCategoryAdd.setImageBitmap(AppUtils.convertPathFileImageAssetsToBitmap(
                    mCategory.getCategoryPath(), this));
            llDelete.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Hàm xóa hạng mục
     *
     * @created_by nxduong on 6/3/2021
     */
    @Override
    public void onClickYesDelete() {
        if (this.mCategory != null) {
            long delete = new CategoryDatabase(this).deleteCategory(mCategory.getCategoryId());
            if (delete == DBUtils.checkDBFail) {
                showCustomToast(getString(R.string.delete_category_fail), AppUtils.TOAST_ERROR);
            } else {
                showCustomToast(getString(R.string.delete_category_success), AppUtils.TOAST_SUCCESS);
                Intent intent = new Intent();
                intent.putExtra("KEY_CATEGORY", mKeyType);
                setResult(RESULT_OK, intent);
                finish();
            }
        }
    }
}