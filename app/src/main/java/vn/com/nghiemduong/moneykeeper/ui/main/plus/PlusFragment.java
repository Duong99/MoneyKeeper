package vn.com.nghiemduong.moneykeeper.ui.main.plus;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.doodle.android.chips.ChipsView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import vn.com.nghiemduong.moneykeeper.R;
import vn.com.nghiemduong.moneykeeper.adapter.CustomSpinnerCategoriesArrayAdapter;
import vn.com.nghiemduong.moneykeeper.data.db.category.CategoryDatabase;
import vn.com.nghiemduong.moneykeeper.data.model.HeaderCategory;
import vn.com.nghiemduong.moneykeeper.data.model.db.Account;
import vn.com.nghiemduong.moneykeeper.data.model.db.Category;

import vn.com.nghiemduong.moneykeeper.ui.base.BaseFragment;
import vn.com.nghiemduong.moneykeeper.ui.dialog.attention.AttentionReportDialog;
import vn.com.nghiemduong.moneykeeper.ui.dialog.date.CustomDateTimeDialog;
import vn.com.nghiemduong.moneykeeper.ui.main.category.choose.ChooseCategoryActivity;

import vn.com.nghiemduong.moneykeeper.ui.main.plus.chooseaccount.ChooseAccountActivity;
import vn.com.nghiemduong.moneykeeper.ui.main.plus.debtor.DebtorActivity;
import vn.com.nghiemduong.moneykeeper.utils.AppConstants;
import vn.com.nghiemduong.moneykeeper.utils.AppPermission;
import vn.com.nghiemduong.moneykeeper.utils.AppUtils;

import static android.app.Activity.RESULT_OK;

/**
 * - @created_by nxduong on 25/1/2021
 **/
public class PlusFragment extends BaseFragment implements PlusMvpView, View.OnClickListener,
        CustomDateTimeDialog.IOnClickSaveDateTime, AppPermission.IOnRequestPermissionCameraResult,
        AttentionReportDialog.IOnClickAttentionReportDialog {
    private View mView;
    private Spinner spinnerCategories;
    private PlusPresenter mPlusPresenter;

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch swNotIncludeReport;
    private EditText etInputAmount;
    private ImageView ivImageCategory, ivImageAccount, ivImageSelected,
            ivImageFromAccount, ivImageToAccount;
    private TextView tvTitleCategory, tvTitleAccount, tvDate, tvTime, tvNameDebtor,
            tvTitleFromAccount, tvTitleToAccount;
    private EditText etExplain;
    private Account mAccount, mFromAccount, mToAccount;
    private Category mCategory;
    private LinearLayout llSelectImage;
    private RelativeLayout rlContentImage, rlLayoutDebtor, rlLayoutDuration,
            rlLayoutTransferAccount, rlChooseCategory, rlChooseAccount;

    private CategoryDatabase mCategoryDatabase;

    private Bitmap image;
    private ChipsView chipViewDebtor;
    private int record = AppConstants.CHI_TIEN; // Biến ghi chép giúp biết dươc ghi chép cái gì, Chi tiền, trả tiền ....

    public PlusFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_plus, container, false);
        init();
        onClickSpinnerCategories();
        getDataBundle();

        swNotIncludeReport.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    new AttentionReportDialog(Objects.requireNonNull(getContext()),
                            PlusFragment.this).show();
                }
            }
        });

        return mView;
    }

    /**
     * Hàm lấy dữ liệu 2 đối tượng Thu tiền (moneyCollect) và Chi tiền (MoneyPay)
     *
     * @created_by nxduong on 5/2/2021
     */
    private void getDataBundle() {

    }

    private void onClickSpinnerCategories() {
        spinnerCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: // Chọn màn hình chi tiền
                        if (record != AppConstants.TRA_NO) {
                            mPlusPresenter.initView(AppConstants.CHI_TIEN);
                        }

                        break;
                    case 1: // Chọn màn hình thu tiền
                        if (record != AppConstants.THU_NO) {
                            mPlusPresenter.initView(AppConstants.THU_TIEN);
                        }
                        break;
                    case 2: // Chọn màn hình cho vay
                        mPlusPresenter.initView(AppConstants.CHO_VAY);
                        break;
                    case 3: // Chọn màn hình đi vay
                        mPlusPresenter.initView(AppConstants.DI_VAY);
                        break;
                    case 4: // Chọn màn hình chuyển khoản
                        mPlusPresenter.initView(AppConstants.CHUYEN_KHOAN);
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    /**
     * Hàm khởi tạo / ánh xạ các view chung cho các layout màn hình chi tiền,
     * thu tiền, cho vay, đi vay có view chung
     *
     * @created_by nxduong on 7/3/2021
     */
    private void init() {
        mPlusPresenter = new PlusPresenter(this, getContext(), getActivity());
        etInputAmount = mView.findViewById(R.id.etInputAmount);
        AppUtils.formatNumberEditText(etInputAmount);

        // View hạng mục, thể loại
        rlChooseCategory = mView.findViewById(R.id.rlChooseCategory);
        rlChooseCategory.setOnClickListener(this);
        ivImageCategory = mView.findViewById(R.id.ivImageCategory);
        tvTitleCategory = mView.findViewById(R.id.tvTitleCategory);

        etExplain = mView.findViewById(R.id.etExplain);

        //Người vay nợ
        rlLayoutDebtor = mView.findViewById(R.id.rlLayoutDebtor);
        rlLayoutDebtor.setOnClickListener(this);

        chipViewDebtor = mView.findViewById(R.id.chipViewDebtor);
        chipViewDebtor.setEnabled(false);
        // Chuyển khoản
        rlLayoutTransferAccount = mView.findViewById(R.id.rlLayoutTransferAccount);
        RelativeLayout rlChooseFromAccount = mView.findViewById(R.id.rlChooseFromAccount);
        rlChooseFromAccount.setOnClickListener(this);
        ivImageFromAccount = mView.findViewById(R.id.ivImageFromAccount);
        tvTitleFromAccount = mView.findViewById(R.id.tvTitleFromAccount);
        tvTitleFromAccount.setTextColor(getResources().getColor(R.color.text_unvalue));

        RelativeLayout rlChooseToAccount = mView.findViewById(R.id.rlChooseToAccount);
        rlChooseToAccount.setOnClickListener(this);
        ivImageToAccount = mView.findViewById(R.id.ivImageToAccount);
        tvTitleToAccount = mView.findViewById(R.id.tvTitleToAccount);
        tvTitleToAccount.setTextColor(getResources().getColor(R.color.text_unvalue));

        // View tài khoản
        rlChooseAccount = mView.findViewById(R.id.rlChooseAccount);
        rlChooseAccount.setOnClickListener(this);
        ivImageAccount = mView.findViewById(R.id.ivImageAccount);
        tvTitleAccount = mView.findViewById(R.id.tvTitleAccount);
        tvTitleAccount.setTextColor(getResources().getColor(R.color.text_unvalue));
        mPlusPresenter.doGetAccountFirstFromDB(getContext());

        // Thời gian
        tvDate = mView.findViewById(R.id.tvDate);
        tvDate.setOnClickListener(this);
        tvDate.setText(UtilsPlus.getDateCurrent());

        tvTime = mView.findViewById(R.id.tvTime);
        tvTime.setOnClickListener(this);
        tvTime.setText(UtilsPlus.getTimeCurrent());

        // Thời hạn
        rlLayoutDuration = mView.findViewById(R.id.rlLayoutDuration);
        rlLayoutDuration.setOnClickListener(this);

        swNotIncludeReport = mView.findViewById(R.id.swNotIncludeReport);

        // Ảnh
        RelativeLayout rlSelectFolder = mView.findViewById(R.id.rlSelectFolder);
        rlSelectFolder.setOnClickListener(this);
        RelativeLayout rlSelectCamera = mView.findViewById(R.id.rlSelectCamera);
        rlSelectCamera.setOnClickListener(this);

        ImageView ivRemoveImageSelected = mView.findViewById(R.id.ivRemoveImageSelected);
        ivRemoveImageSelected.setOnClickListener(this);

        llSelectImage = mView.findViewById(R.id.llSelectImage);
        rlContentImage = mView.findViewById(R.id.rlContentImage);
        ivImageSelected = mView.findViewById(R.id.ivImageSelected);

        LinearLayout llDelete = mView.findViewById(R.id.llDelete);
        llDelete.setOnClickListener(this);

        LinearLayout llSave = mView.findViewById(R.id.llSave);
        llSave.setOnClickListener(this);

        ImageView ivSaveDonePlus = mView.findViewById(R.id.ivSaveDonePlus);
        ivSaveDonePlus.setOnClickListener(this);

        mCategoryDatabase = new CategoryDatabase(getContext());

        spinnerCategories = mView.findViewById(R.id.spinnerCategories);
        mPlusPresenter.addCategories();
    }

    /**
     * Khởi tạo / Ánh xạ view cho màn hình chi tiền
     *
     * @created_by nxduong on 7/3/2021
     */
    @Override
    public void initViewPay() {
        if (spinnerCategories.getSelectedItemPosition() == 0) {
            record = AppConstants.CHI_TIEN;
            etInputAmount.setTextColor(getResources().getColor(R.color.input_amount_red_pay));
            rlChooseCategory.setVisibility(View.VISIBLE);
            rlLayoutDebtor.setVisibility(View.GONE);
            rlLayoutDuration.setVisibility(View.GONE);
            rlLayoutTransferAccount.setVisibility(View.GONE); // Ẩn chuyển tài khoản

            if (mCategory != null) {
                if (mCategory.getType() != AppConstants.CHI_TIEN) {
                    resultChooseCategory(null);
                }
            }
        } else {
            spinnerCategories.setSelection(0);
        }

    }

    /**
     * Màn hình trả nợ
     *
     * @created_by nxduong on 10/3/2021
     */

    @Override
    public void initViewDebtPay() {
        record = AppConstants.TRA_NO;
        spinnerCategories.setSelection(0);
        etInputAmount.setTextColor(getResources().getColor(R.color.input_amount_green_collect));
        rlChooseCategory.setVisibility(View.VISIBLE);
        rlChooseAccount.setVisibility(View.VISIBLE);
        rlLayoutDebtor.setVisibility(View.VISIBLE);
        rlLayoutDuration.setVisibility(View.GONE);

        rlLayoutTransferAccount.setVisibility(View.GONE); // Ẩn chuyển tài khoản
    }

    /**
     * Khởi tạo / Ánh xạ view cho màn hình thu tiền
     *
     * @created_by nxduong on 7/3/2021
     */

    @Override
    public void initViewCollect() {
        if (spinnerCategories.getSelectedItemPosition() == 1) {
            record = AppConstants.THU_TIEN;
            etInputAmount.setTextColor(getResources().getColor(R.color.input_amount_green_collect));
            rlChooseCategory.setVisibility(View.VISIBLE);
            rlChooseAccount.setVisibility(View.VISIBLE);
            rlLayoutDebtor.setVisibility(View.GONE);
            rlLayoutDuration.setVisibility(View.GONE);

            rlLayoutTransferAccount.setVisibility(View.GONE); // Ẩn chuyển tài khoản

            if (mCategory != null) {
                if (mCategory.getType() != AppConstants.THU_TIEN) {
                    resultChooseCategory(null);
                }
            }
        } else {
            spinnerCategories.setSelection(1);
        }
    }

    /**
     * Màn hình thu nợ
     *
     * @created_by nxduong on 10/3/2021
     */

    @Override
    public void initDebtCollect() {
        record = AppConstants.THU_NO;
        spinnerCategories.setSelection(1);
        etInputAmount.setTextColor(getResources().getColor(R.color.input_amount_green_collect));
        rlChooseCategory.setVisibility(View.VISIBLE);
        rlChooseAccount.setVisibility(View.VISIBLE);
        rlLayoutDebtor.setVisibility(View.VISIBLE);
        rlLayoutDuration.setVisibility(View.GONE);

        rlLayoutTransferAccount.setVisibility(View.GONE); // Ẩn chuyển tài khoản
    }

    /**
     * Khởi tạo / Ánh xạ view cho màn hình cho vay
     *
     * @created_by nxduong on 7/3/2021
     */
    @Override
    public void initViewLoan() {
        if (spinnerCategories.getSelectedItemPosition() == 2) {
            record = AppConstants.CHO_VAY;

            etInputAmount.setTextColor(getResources().getColor(R.color.input_amount_red_pay));
            rlChooseCategory.setVisibility(View.VISIBLE);
            rlChooseAccount.setVisibility(View.VISIBLE);
            rlLayoutDebtor.setVisibility(View.VISIBLE);
            rlLayoutDuration.setVisibility(View.VISIBLE);

            rlLayoutTransferAccount.setVisibility(View.GONE); // Ẩn chuyển tài khoản

            if (mCategory == null) {
                resultChooseCategory(mCategoryDatabase.getCategory(AppConstants.CHO_VAY_ID)); // set hạng mục cho vay
            } else {
                if (mCategory.getCategoryId() != AppConstants.CHO_VAY_ID) {
                    resultChooseCategory(mCategoryDatabase.getCategory(AppConstants.CHO_VAY_ID));
                }
            }
        } else {
            spinnerCategories.setSelection(2);
        }
    }

    /**
     * Khởi tạo / Ánh xạ view cho màn hình đi vay
     *
     * @created_by nxduong on 7/3/2021
     */

    @Override
    public void initViewBorrow() {
        if (spinnerCategories.getSelectedItemPosition() == 3) {
            record = AppConstants.DI_VAY;
            etInputAmount.setTextColor(getResources().getColor(R.color.input_amount_green_collect));
            rlChooseCategory.setVisibility(View.VISIBLE);
            rlChooseCategory.setVisibility(View.VISIBLE);
            rlLayoutDebtor.setVisibility(View.VISIBLE);
            rlLayoutDuration.setVisibility(View.VISIBLE);

            rlLayoutTransferAccount.setVisibility(View.GONE);// Ẩn chuyển tài khoản

            // set hạng mục đi vay
            if (mCategory == null) {
                resultChooseCategory(mCategoryDatabase.getCategory(AppConstants.DI_VAY_ID));
            } else {
                if (mCategory.getCategoryId() != AppConstants.DI_VAY_ID) {
                    resultChooseCategory(mCategoryDatabase.getCategory(AppConstants.DI_VAY_ID));
                }
            }
        } else {
            spinnerCategories.setSelection(3);
        }


    }

    @Override
    public void initViewTransfer() {
        etInputAmount.setTextColor(getResources().getColor(R.color.input_amount_blue_transfer));
        rlChooseCategory.setVisibility(View.GONE);
        rlChooseAccount.setVisibility(View.GONE);
        rlLayoutDebtor.setVisibility(View.GONE);
        rlLayoutDuration.setVisibility(View.GONE);

        rlLayoutTransferAccount.setVisibility(View.VISIBLE);// Hiện chuyển tài khoản
    }

    @Override
    public void resultListCategories(ArrayList<HeaderCategory> listCategory) {
        CustomSpinnerCategoriesArrayAdapter mSpinnerAdapter = new CustomSpinnerCategoriesArrayAdapter(
                Objects.requireNonNull(getContext()), listCategory);
        spinnerCategories.setAdapter(mSpinnerAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlChooseCategory: // Chọn hạng mục
                try {
                    Intent intentChooseCategory = new Intent(getContext(), ChooseCategoryActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("BUNDLE_CATEGORY", mCategory);
                    intentChooseCategory.putExtra("BUNDLE", bundle);
                    startActivityForResult(intentChooseCategory,
                            ChooseCategoryActivity.REQUEST_CODE_CHOOSE_CATEGORY);
                } catch (Exception e) {
                    AppUtils.handlerException(e);
                }
                break;

            case R.id.rlLayoutDebtor: // Chọn người vay nợ
                try {
                    Intent intentDebtor = new Intent(getContext(), DebtorActivity.class);
                    startActivityForResult(intentDebtor, DebtorActivity.REQUEST_CODE_CHOOSE_DEBTOR);
                } catch (Exception e) {
                    AppUtils.handlerException(e);
                }
                break;

            case R.id.rlChooseAccount: // Chọn tài khoản
                try {
                    Intent intentChooseAccount = new Intent(getContext(), ChooseAccountActivity.class);
                    Bundle bundleChooseAccount = new Bundle();
                    bundleChooseAccount.putSerializable("BUNDLE_ACCOUNT", mAccount);
                    intentChooseAccount.putExtra("BUNDLE", bundleChooseAccount);
                    startActivityForResult(intentChooseAccount, ChooseAccountActivity.REQUEST_CODE_CHOOSE_ACCOUNT);
                } catch (Exception e) {
                    AppUtils.handlerException(e);
                }
                break;

            case R.id.tvDate: // Chọn ngày tháng năm
                try {
                    new CustomDateTimeDialog(Objects.requireNonNull(getContext()),
                            CustomDateTimeDialog.KEY_CALENDAR,
                            tvDate.getText().toString(), tvTime.getText().toString(),
                            this).show();
                } catch (Exception e) {
                    AppUtils.handlerException(e);
                }

                break;
            case R.id.tvTime: // Chọn thời gian
                try {
                    new CustomDateTimeDialog(Objects.requireNonNull(getContext()),
                            CustomDateTimeDialog.KEY_WATCH,
                            tvDate.getText().toString(), tvTime.getText().toString(),
                            this).show();
                } catch (Exception e) {
                    AppUtils.handlerException(e);
                }
                break;

            case R.id.rlSelectFolder: // Chọn ảnh từ thư mục
                try {
                    Intent intentFolder = new Intent(Intent.ACTION_PICK);
                    intentFolder.setType("image/*");
                    startActivityForResult(intentFolder, AppConstants.REQUEST_CODE_IMAGE_FROM_FOLDER);
                } catch (Exception e) {
                    AppUtils.handlerException(e);
                }
                break;

            case R.id.rlSelectCamera: // Chọn ảnh bằng chụp ảnh từ camera
                try {
                    new AppPermission().requestCameraPermission(getContext(), this);
                } catch (Exception e) {
                    AppUtils.handlerException(e);
                }
                break;

            case R.id.ivRemoveImageSelected: // Xóa ảnh đã được chọn
                try {
                    llSelectImage.setVisibility(View.VISIBLE);
                    rlContentImage.setVisibility(View.GONE);
                    image = null;
                } catch (Exception e) {
                    AppUtils.handlerException(e);
                }
                break;

            case R.id.rlChooseFromAccount: // Chọn tài khoản chuyển tiền
                try {
                    Intent intentChooseAccount = new Intent(getContext(), ChooseAccountActivity.class);
                    Bundle bundleChooseAccount = new Bundle();
                    bundleChooseAccount.putSerializable("BUNDLE_ACCOUNT", mFromAccount);
                    intentChooseAccount.putExtra("BUNDLE", bundleChooseAccount);
                    startActivityForResult(intentChooseAccount,
                            ChooseAccountActivity.REQUEST_CODE_FROM_ACCOUNT);
                } catch (Exception e) {
                    AppUtils.handlerException(e);
                }
                break;

            case R.id.rlChooseToAccount: // Chọn tài khoản nhận tiền
                try {
                    Intent intentChooseAccount = new Intent(getContext(), ChooseAccountActivity.class);
                    Bundle bundleChooseAccount = new Bundle();
                    bundleChooseAccount.putSerializable("BUNDLE_ACCOUNT", mToAccount);
                    intentChooseAccount.putExtra("BUNDLE", bundleChooseAccount);
                    startActivityForResult(intentChooseAccount,
                            ChooseAccountActivity.REQUEST_CODE_TO_ACCOUNT);
                } catch (Exception e) {
                    AppUtils.handlerException(e);
                }
                break;

            case R.id.llDelete:

                break;

            case R.id.llSave:

                break;

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (data != null) {
                if (requestCode == ChooseCategoryActivity.REQUEST_CODE_CHOOSE_CATEGORY) {
                    mPlusPresenter.doGetChooseCategory(data);
                }

                if (requestCode == ChooseAccountActivity.REQUEST_CODE_CHOOSE_ACCOUNT ||
                        requestCode == ChooseAccountActivity.REQUEST_CODE_TO_ACCOUNT ||
                        requestCode == ChooseAccountActivity.REQUEST_CODE_FROM_ACCOUNT) {
                    mPlusPresenter.doGetChooseAccount(data, requestCode);
                }

                if (requestCode == AppConstants.REQUEST_CODE_IMAGE_FROM_FOLDER) {
                    try {
                        Uri uri = data.getData();
                        image = MediaStore.Images.Media.getBitmap(
                                Objects.requireNonNull(getContext())
                                        .getContentResolver(), uri);
                        setValueImageFolderOrCamera();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (requestCode == AppConstants.REQUEST_CODE_IMAGE_FROM_CAMERA) {
                    image = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
                    setValueImageFolderOrCamera();
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == AppPermission.PERMISSIONS_REQUEST_CAMERA) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                onPermissionCameraGranted();
            } else {
                onPermissionCameraNotGranted();
            }
        }

    }

    private void setValueImageFolderOrCamera() {
        ivImageSelected.setImageBitmap(image);
        llSelectImage.setVisibility(View.GONE);
        rlContentImage.setVisibility(View.VISIBLE);
    }

    /**
     * Hàm trả về kết quả khi chọn hạng mục
     *
     * @created_by nxduong on 5/3/2021
     */
    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void resultChooseCategory(Category category) {
        this.mCategory = category;
        if (mCategory != null) {
            ivImageCategory.setImageBitmap(AppUtils.convertPathFileImageAssetsToBitmap(
                    mCategory.getCategoryPath(), Objects.requireNonNull(getContext())));
            tvTitleCategory.setText(mCategory.getCategoryName());
            tvTitleCategory.setTextColor(getResources().getColor(R.color.text_valuable));
        } else {
            ivImageCategory.setImageBitmap(null);
            ivImageCategory.setBackground(getResources().getDrawable(R.drawable.ic_help_white));
            tvTitleCategory.setText(getResources().getString(R.string.select_item));
            tvTitleCategory.setTextColor(getResources().getColor(R.color.text_unvalue));
        }
    }

    /**
     * Kết quả trả về tài khoản được chọn
     *
     * @created_by nxduong on 5/3/2021
     */
    @Override
    public void resultChooseAccount(Account account, int request_code) {
        if (account != null) {
            switch (request_code) {
                case ChooseAccountActivity.REQUEST_CODE_CHOOSE_ACCOUNT:
                    this.mAccount = account;
                    setViewAccount(ivImageAccount, tvTitleAccount, mAccount);
                    break;
                case ChooseAccountActivity.REQUEST_CODE_FROM_ACCOUNT:
                    this.mFromAccount = account;
                    setViewAccount(ivImageFromAccount, tvTitleFromAccount, mFromAccount);
                    break;
                case ChooseAccountActivity.REQUEST_CODE_TO_ACCOUNT:
                    this.mToAccount = account;
                    setViewAccount(ivImageToAccount, tvTitleToAccount, mToAccount);
                    break;
            }
        }
    }

    /**
     * Hàm trả về kết quả tài khoản đầu tiên trong database
     *
     * @created_by nxduong on 5/3/2021
     */
    @Override
    public void resultGetAccountFirstFromDB(Account account) {
        this.mAccount = account;
        if (mAccount != null) {
            setViewAccount(ivImageAccount, tvTitleAccount, mAccount);
        }
    }

    @Override
    public void saveDateTime(String date, String time) {
        tvDate.setText(date);
        tvTime.setText(time);
    }

    /**
     * Hàm mở camera khi có quền
     *
     * @created_by nxduong on 8/3/2021
     */
    @Override
    public void onPermissionCameraGranted() {
        Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intentCamera, AppConstants.REQUEST_CODE_IMAGE_FROM_CAMERA);
    }

    @Override
    public void onPermissionCameraNotGranted() {

    }

    @Override
    public void onNoReport() {
        swNotIncludeReport.setChecked(false);
    }

    @Override
    public void onYesReport() {
        swNotIncludeReport.setChecked(true);
    }

    // Hàm set giá trị view cho tài khoản
    public void setViewAccount(ImageView ivImageAccount, TextView tvTitleAccount, Account account) {
        ivImageAccount.setImageBitmap(AppUtils.convertPathFileImageAssetsToBitmap(
                account.getAccountTypePath(), Objects.requireNonNull(getContext())));
        tvTitleAccount.setText(mAccount.getAccountName());
        tvTitleAccount.setTextColor(getResources().getColor(R.color.text_valuable));
    }
}