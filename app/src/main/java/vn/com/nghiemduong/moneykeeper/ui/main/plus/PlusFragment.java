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

import com.google.android.material.chip.Chip;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import vn.com.nghiemduong.moneykeeper.R;
import vn.com.nghiemduong.moneykeeper.adapter.CustomSpinnerCategoriesArrayAdapter;
import vn.com.nghiemduong.moneykeeper.data.db.account.AccountDatabase;
import vn.com.nghiemduong.moneykeeper.data.db.category.CategoryDatabase;
import vn.com.nghiemduong.moneykeeper.data.db.record.RecordDatabase;
import vn.com.nghiemduong.moneykeeper.data.model.HeaderCategory;
import vn.com.nghiemduong.moneykeeper.data.model.db.Account;
import vn.com.nghiemduong.moneykeeper.data.model.db.Category;

import vn.com.nghiemduong.moneykeeper.data.model.db.Record;
import vn.com.nghiemduong.moneykeeper.ui.base.BaseFragment;
import vn.com.nghiemduong.moneykeeper.ui.dialog.attention.AttentionDeleteDialog;
import vn.com.nghiemduong.moneykeeper.ui.dialog.attention.AttentionReportDialog;
import vn.com.nghiemduong.moneykeeper.ui.dialog.date.CustomDateTimeDialog;
import vn.com.nghiemduong.moneykeeper.ui.main.category.choose.ChooseCategoryActivity;

import vn.com.nghiemduong.moneykeeper.ui.main.plus.chooseaccount.ChooseAccountActivity;
import vn.com.nghiemduong.moneykeeper.ui.main.plus.debtor.DebtorActivity;
import vn.com.nghiemduong.moneykeeper.utils.AppConstants;
import vn.com.nghiemduong.moneykeeper.utils.AppPermission;
import vn.com.nghiemduong.moneykeeper.utils.AppUtils;
import vn.com.nghiemduong.moneykeeper.utils.DBUtils;

import static android.app.Activity.RESULT_OK;

/**
 * - @created_by nxduong on 25/1/2021
 **/
public class PlusFragment extends BaseFragment implements PlusMvpView, View.OnClickListener,
        CustomDateTimeDialog.IOnClickSaveDateTime, AppPermission.IOnRequestPermissionCameraResult,
        AttentionReportDialog.IOnClickAttentionReportDialog, AttentionDeleteDialog.IOnClickAttentionDialog {
    private View mView;
    private Spinner spinnerCategories;
    private PlusPresenter mPlusPresenter;

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch swNotIncludeReport;
    private EditText etInputAmount;
    private ImageView ivImageCategory, ivImageAccount, ivImageSelected,
            ivImageFromAccount, ivImageToAccount;
    private TextView tvTitleCategory, tvTitleAccount, tvDate, tvTime, tvDateDuration,
            tvTitleFromAccount, tvTitleToAccount, tvDebtor;
    private EditText etDescription;
    private Account mAccount, mToAccount;
    private Category mCategory;
    private LinearLayout llSelectImage, llDelete;
    private RelativeLayout rlContentImage, rlLayoutDebtor, rlLayoutDuration,
            rlLayoutTransferAccount, rlChooseCategory, rlChooseAccount;

    private CategoryDatabase mCategoryDatabase;
    private AccountDatabase mAccountDatabase;
    private Record mRecord;

    private Bitmap imageBitmap;
    private Chip chipDebtor;
    private String mDebtor = "", mDateDuration = "";
    private int recordConstant = AppConstants.CHI_TIEN; // Biến ghi chép giúp biết dươc ghi chép cái gì, Chi tiền, trả tiền ....

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

        chipDebtor.setOnCloseIconClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDebtor = "";
                chipDebtor.setVisibility(View.GONE);
                tvDebtor.setVisibility(View.VISIBLE);
            }
        });

        chipDebtor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intentDebtor = new Intent(getContext(), DebtorActivity.class);
                    if (recordConstant == AppConstants.CHO_VAY || recordConstant == AppConstants.THU_NO) {
                        startActivityForResult(intentDebtor, DebtorActivity.REQUEST_CODE_CHOOSE_BORROWER);
                    }

                    if (recordConstant == AppConstants.DI_VAY || recordConstant == AppConstants.TRA_NO) {
                        startActivityForResult(intentDebtor, DebtorActivity.REQUEST_CODE_CHOOSE_LENDER);
                    }
                } catch (Exception e) {
                    vn.com.nghiemduong.moneykeeper.utils.AppUtils.handlerException(e);
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
                        if (recordConstant != AppConstants.TRA_NO) {
                            mPlusPresenter.initView(AppConstants.CHI_TIEN);
                        }

                        break;
                    case 1: // Chọn màn hình thu tiền
                        if (recordConstant != AppConstants.THU_NO) {
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
        vn.com.nghiemduong.moneykeeper.utils.AppUtils.formatNumberEditText(etInputAmount);

        // View hạng mục, thể loại
        rlChooseCategory = mView.findViewById(R.id.rlChooseCategory);
        rlChooseCategory.setOnClickListener(this);
        ivImageCategory = mView.findViewById(R.id.ivImageCategory);
        tvTitleCategory = mView.findViewById(R.id.tvTitleCategory);

        etDescription = mView.findViewById(R.id.etDescription);
        AppUtils.addTextChangeEditText(etDescription);

        //Người vay nợ
        rlLayoutDebtor = mView.findViewById(R.id.rlLayoutDebtor);
        rlLayoutDebtor.setOnClickListener(this);

        chipDebtor = mView.findViewById(R.id.chipDebtor);
        chipDebtor.setCheckedIconVisible(false);
        tvDebtor = mView.findViewById(R.id.tvDebtor);

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
        tvDate.setText(AppUtils.getDateCurrent(getActivity()));

        tvTime = mView.findViewById(R.id.tvTime);
        tvTime.setOnClickListener(this);
        tvTime.setText(AppUtils.getTimeCurrent());

        // Thời hạn
        rlLayoutDuration = mView.findViewById(R.id.rlLayoutDuration);
        rlLayoutDuration.setOnClickListener(this);
        tvDateDuration = mView.findViewById(R.id.tvDateDuration);
        //tvDateDuration.setText(UtilsPlus.getDateCurrent());
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

        llDelete = mView.findViewById(R.id.llDelete);
        llDelete.setOnClickListener(this);

        LinearLayout llSave = mView.findViewById(R.id.llSave);
        llSave.setOnClickListener(this);

        ImageView ivSaveDonePlus = mView.findViewById(R.id.ivSaveDonePlus);
        ivSaveDonePlus.setOnClickListener(this);

        mCategoryDatabase = new CategoryDatabase(getContext());
        mAccountDatabase = new AccountDatabase(getContext());

        spinnerCategories = mView.findViewById(R.id.spinnerCategories);
        mPlusPresenter.addCategories();

        mPlusPresenter.doGetBundleRecord(this);
    }

    /**
     * Hàm kiểm tra xem là thêm hay sửa
     * nếu record = null là thêm ngược lại là sửa
     *
     * @param record ghi chép
     * @created_by nxduong on 11/3/2021
     */
    @Override
    public void resultGetBundleRecord(Record record) {
        this.mRecord = record;
        if (mRecord != null) {
            llDelete.setVisibility(View.VISIBLE);
            etInputAmount.setText(String.valueOf(mRecord.getAmount()));
            etDescription.setText(mRecord.getDescription());
            tvDate.setText(mRecord.getDate());
            tvTime.setText(mRecord.getTime());

            mCategory = mCategoryDatabase.getCategory(mRecord.getCategoryId());
            if (mCategory != null) {
                resultChooseCategory(mCategory);
            }

            mDebtor = mRecord.getDebtor();
            if (mDebtor != null && !mDebtor.equals("")) {
                chipDebtor.setVisibility(View.VISIBLE);
                chipDebtor.setText(mDebtor);
                tvDebtor.setVisibility(View.GONE);
            }

            mDateDuration = mRecord.getDateDuration();
            if (mDateDuration != null && !mDateDuration.equals("")) {
                tvDateDuration.setText(mDateDuration);
            }

            mAccount = mAccountDatabase.getAccount(mRecord.getAccountId());
            if (mAccount != null) {
                resultChooseAccount(mAccount, ChooseAccountActivity.REQUEST_CODE_CHOOSE_ACCOUNT);
            }

            mToAccount = mAccountDatabase.getAccount(mRecord.getToAccountId());
            if (mToAccount != null) {
                resultChooseAccount(mAccount, ChooseAccountActivity.REQUEST_CODE_TO_ACCOUNT);
            }

            if (mRecord.getReport() == AppConstants.KHONG_BAO_CAO) {
                swNotIncludeReport.setChecked(true);
            }

            imageBitmap = vn.com.nghiemduong.moneykeeper.utils.AppUtils.convertByteArrayToBitmap(mRecord.getImage());
            if (imageBitmap != null) {
                setValueImageFolderOrCamera();
            }
            mPlusPresenter.initView(mRecord.getType());
        }
    }

    /**
     * Khởi tạo / Ánh xạ view cho màn hình chi tiền
     *
     * @created_by nxduong on 7/3/2021
     */
    @Override
    public void initViewPay() {
        if (spinnerCategories.getSelectedItemPosition() == 0) {
            recordConstant = AppConstants.CHI_TIEN;
            etInputAmount.setTextColor(getResources().getColor(R.color.input_amount_red_pay));
            rlChooseCategory.setVisibility(View.VISIBLE);
            rlChooseAccount.setVisibility(View.VISIBLE);
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
        recordConstant = AppConstants.TRA_NO;
        spinnerCategories.setSelection(0);
        etInputAmount.setTextColor(getResources().getColor(R.color.input_amount_green_collect));
        rlChooseCategory.setVisibility(View.VISIBLE);
        rlChooseAccount.setVisibility(View.VISIBLE);
        rlLayoutDebtor.setVisibility(View.VISIBLE);
        rlLayoutDuration.setVisibility(View.GONE);
        tvDebtor.setText(getString(R.string.lender));

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
            recordConstant = AppConstants.THU_TIEN;
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
        recordConstant = AppConstants.THU_NO;
        spinnerCategories.setSelection(1);
        etInputAmount.setTextColor(getResources().getColor(R.color.input_amount_green_collect));
        rlChooseCategory.setVisibility(View.VISIBLE);
        rlChooseAccount.setVisibility(View.VISIBLE);
        rlLayoutDebtor.setVisibility(View.VISIBLE);
        rlLayoutDuration.setVisibility(View.GONE);
        tvDebtor.setText(getString(R.string.borrower));

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
            recordConstant = AppConstants.CHO_VAY;

            etInputAmount.setTextColor(getResources().getColor(R.color.input_amount_red_pay));
            rlChooseCategory.setVisibility(View.VISIBLE);
            rlChooseAccount.setVisibility(View.VISIBLE);
            rlLayoutDebtor.setVisibility(View.VISIBLE);
            rlLayoutDuration.setVisibility(View.VISIBLE);

            tvDebtor.setText(getString(R.string.borrower));

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
            recordConstant = AppConstants.DI_VAY;
            etInputAmount.setTextColor(getResources().getColor(R.color.input_amount_green_collect));
            rlChooseCategory.setVisibility(View.VISIBLE);
            rlChooseAccount.setVisibility(View.VISIBLE);
            rlLayoutDebtor.setVisibility(View.VISIBLE);
            rlLayoutDuration.setVisibility(View.VISIBLE);

            tvDebtor.setText(getString(R.string.lender));

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

    /**
     * MÀn hình chuyển khoản
     *
     * @created_by nxduong on 10/3/2021
     */

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
                    vn.com.nghiemduong.moneykeeper.utils.AppUtils.handlerException(e);
                }
                break;

            case R.id.rlLayoutDebtor: // Chọn người vay nợ
                try {
                    Intent intentDebtor = new Intent(getContext(), DebtorActivity.class);
                    if (recordConstant == AppConstants.CHO_VAY || recordConstant == AppConstants.THU_NO) {
                        intentDebtor.putExtra(DebtorActivity.KEY_CONTACT_ACTIVITY_TYPE,
                                DebtorActivity.REQUEST_CODE_CHOOSE_BORROWER);
                        startActivityForResult(intentDebtor, DebtorActivity.REQUEST_CODE_CHOOSE_BORROWER);
                    }

                    if (recordConstant == AppConstants.DI_VAY || recordConstant == AppConstants.TRA_NO) {
                        intentDebtor.putExtra(DebtorActivity.KEY_CONTACT_ACTIVITY_TYPE,
                                DebtorActivity.REQUEST_CODE_CHOOSE_LENDER);
                        startActivityForResult(intentDebtor, DebtorActivity.REQUEST_CODE_CHOOSE_LENDER);
                    }

                } catch (Exception e) {
                    vn.com.nghiemduong.moneykeeper.utils.AppUtils.handlerException(e);
                }
                break;

            case R.id.rlChooseAccount: // Chọn tài khoản
                try {
                    Intent intentChooseAccount = new Intent(getContext(), ChooseAccountActivity.class);
                    Bundle bundleChooseAccount = new Bundle();
                    bundleChooseAccount.putSerializable("BUNDLE_ACCOUNT", mAccount);
                    intentChooseAccount.putExtra("BUNDLE", bundleChooseAccount);
                    startActivityForResult(intentChooseAccount,
                            ChooseAccountActivity.REQUEST_CODE_CHOOSE_ACCOUNT);
                } catch (Exception e) {
                    vn.com.nghiemduong.moneykeeper.utils.AppUtils.handlerException(e);
                }
                break;

            case R.id.tvDate: // Chọn ngày tháng năm
                try {
                    new CustomDateTimeDialog(Objects.requireNonNull(getContext()),
                            CustomDateTimeDialog.KEY_CALENDAR,
                            tvDate.getText().toString(), tvTime.getText().toString(),
                            this).show();
                } catch (Exception e) {
                    vn.com.nghiemduong.moneykeeper.utils.AppUtils.handlerException(e);
                }

                break;
            case R.id.tvTime: // Chọn thời gian
                try {
                    new CustomDateTimeDialog(Objects.requireNonNull(getContext()),
                            CustomDateTimeDialog.KEY_WATCH,
                            tvDate.getText().toString(), tvTime.getText().toString(),
                            this).show();
                } catch (Exception e) {
                    vn.com.nghiemduong.moneykeeper.utils.AppUtils.handlerException(e);
                }
                break;

            case R.id.rlLayoutDuration: // thời ngày thời hạn
                try {
                    new CustomDateTimeDialog(Objects.requireNonNull(getContext()),
                            CustomDateTimeDialog.KEY_JUST_CALENDAR,
                            tvDateDuration.getText().toString(), tvTime.getText().toString(),
                            this).show();
                } catch (Exception e) {
                    vn.com.nghiemduong.moneykeeper.utils.AppUtils.handlerException(e);
                }
                break;

            case R.id.rlSelectFolder: // Chọn ảnh từ thư mục
                try {
                    Intent intentFolder = new Intent(Intent.ACTION_PICK);
                    intentFolder.setType("image/*");
                    startActivityForResult(intentFolder, AppConstants.REQUEST_CODE_IMAGE_FROM_FOLDER);
                } catch (Exception e) {
                    vn.com.nghiemduong.moneykeeper.utils.AppUtils.handlerException(e);
                }
                break;

            case R.id.rlSelectCamera: // Chọn ảnh bằng chụp ảnh từ camera
                try {
                    new AppPermission().requestCameraPermission(getContext(),
                            getActivity(), this);
                } catch (Exception e) {
                    vn.com.nghiemduong.moneykeeper.utils.AppUtils.handlerException(e);
                }
                break;

            case R.id.ivRemoveImageSelected: // Xóa ảnh đã được chọn
                try {
                    llSelectImage.setVisibility(View.VISIBLE);
                    rlContentImage.setVisibility(View.GONE);
                    imageBitmap = null;
                } catch (Exception e) {
                    vn.com.nghiemduong.moneykeeper.utils.AppUtils.handlerException(e);
                }
                break;

            case R.id.rlChooseFromAccount: // Chọn tài khoản chuyển tiền
                try {
                    Intent intentChooseAccount = new Intent(getContext(), ChooseAccountActivity.class);
                    Bundle bundleChooseAccount = new Bundle();
                    bundleChooseAccount.putSerializable("BUNDLE_ACCOUNT", mAccount);
                    intentChooseAccount.putExtra("BUNDLE", bundleChooseAccount);
                    startActivityForResult(intentChooseAccount,
                            ChooseAccountActivity.REQUEST_CODE_FROM_ACCOUNT);
                } catch (Exception e) {
                    vn.com.nghiemduong.moneykeeper.utils.AppUtils.handlerException(e);
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
                    vn.com.nghiemduong.moneykeeper.utils.AppUtils.handlerException(e);
                }
                break;

            case R.id.llDelete:
                try {
                    new AttentionDeleteDialog(getContext(), this,
                            AttentionDeleteDialog.ATTENTION_DELETE_DATA).show();
                } catch (Exception e) {
                    vn.com.nghiemduong.moneykeeper.utils.AppUtils.handlerException(e);
                }
                break;

            case R.id.llSave:
                try {
                    int amount = Integer.parseInt(vn.com.nghiemduong.moneykeeper.utils.AppUtils.getEditTextFormatNumber(etInputAmount));
                    String explain = vn.com.nghiemduong.moneykeeper.utils.AppUtils.getEditText(etDescription);
                    String date = vn.com.nghiemduong.moneykeeper.utils.AppUtils.formatDateDefault(tvDate.getText().toString(), getActivity());
                    String time = tvTime.getText().toString();

                    int report = AppConstants.CO_BAO_CAO;
                    if (swNotIncludeReport.isChecked()) {
                        report = AppConstants.KHONG_BAO_CAO;
                    }

                    byte[] image = vn.com.nghiemduong.moneykeeper.utils.AppUtils.convertBitmapToByteArray(imageBitmap);

                    mPlusPresenter.saveRecord(mRecord, amount, mCategory, mDebtor, explain, date, time,
                            mAccount, mToAccount, mDateDuration, report, image, recordConstant);
                } catch (Exception e) {
                    vn.com.nghiemduong.moneykeeper.utils.AppUtils.handlerException(e);
                }
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

                if (requestCode == DebtorActivity.REQUEST_CODE_CHOOSE_LENDER ||
                        requestCode == DebtorActivity.REQUEST_CODE_CHOOSE_BORROWER) {
                    mPlusPresenter.doGetDebtor(data);
                }

                if (requestCode == AppConstants.REQUEST_CODE_IMAGE_FROM_FOLDER) {
                    try {
                        Uri uri = data.getData();
                        imageBitmap = MediaStore.Images.Media.getBitmap(
                                Objects.requireNonNull(getContext())
                                        .getContentResolver(), uri);
                        setValueImageFolderOrCamera();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (requestCode == AppConstants.REQUEST_CODE_IMAGE_FROM_CAMERA) {
                    imageBitmap = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
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
        if (imageBitmap != null) {
            ivImageSelected.setImageBitmap(imageBitmap);
            llSelectImage.setVisibility(View.GONE);
            rlContentImage.setVisibility(View.VISIBLE);
        }
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
            ivImageCategory.setImageBitmap(vn.com.nghiemduong.moneykeeper.utils.AppUtils.convertPathFileImageAssetsToBitmap(
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

    @Override
    public void resultChooseDebtor(Record debtor) {
        this.mDebtor = debtor.getDebtor();
        if (!mDebtor.equals("")) {
            tvDebtor.setVisibility(View.GONE);
            chipDebtor.setVisibility(View.VISIBLE);
            chipDebtor.setText(mDebtor);
        } else {
            chipDebtor.setVisibility(View.GONE);
            tvDebtor.setVisibility(View.VISIBLE);
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
                    this.mAccount = account;
                    setViewAccount(ivImageFromAccount, tvTitleFromAccount, mAccount);
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
    public void showCustomToastChooseCategoryWarring(String message) {
        showCustomToast(message, AppConstants.TOAST_WARRING);
        tvTitleCategory.setTextColor(getResources().getColor(R.color.text_warring));
    }

    @Override
    public void showCustomToastChooseAccountWarring(String message) {
        showCustomToast(message, AppConstants.TOAST_WARRING);
        tvTitleAccount.setTextColor(getResources().getColor(R.color.text_warring));
    }

    @Override
    public void showCustomToastChooseFromAccountWarring(String message) {
        showCustomToast(message, AppConstants.TOAST_WARRING);
        tvTitleFromAccount.setTextColor(getResources().getColor(R.color.text_warring));
    }

    @Override
    public void showCustomToastChooseToAccountWarring(String message) {
        showCustomToast(message, AppConstants.TOAST_WARRING);
        tvTitleToAccount.setTextColor(getResources().getColor(R.color.text_warring));
    }

    @Override
    public void showCustomToastChooseDebtorWarring(String message) {
        showCustomToast(message, AppConstants.TOAST_WARRING);
        tvDebtor.setTextColor(getResources().getColor(R.color.text_warring));
    }

    @Override
    public void showCustomToastChooseDateDurationWarring(String message) {
        showCustomToast(message, AppConstants.TOAST_ERROR);
    }

    @Override
    public void saveRecordSuccess(String message) {
        showCustomToast(message, AppConstants.TOAST_SUCCESS);
        mCategory = null;
        mToAccount = null;
        resultChooseAccount(null, ChooseAccountActivity.REQUEST_CODE_TO_ACCOUNT);
        resultChooseCategory(mCategory);
        chipDebtor.setText(null);
        chipDebtor.setVisibility(View.GONE);
        tvDebtor.setVisibility(View.VISIBLE);
        etInputAmount.setText(getString(R.string._0));
        etDescription.setText(null);

        if (mRecord != null) {
            onBackPressed();
        }
    }

    @Override
    public void saveRecordFail(String message) {
        showCustomToast(message, AppConstants.TOAST_ERROR);
    }

    @Override
    public void saveDateTime(String date, String time) {
        tvDate.setText(vn.com.nghiemduong.moneykeeper.utils.AppUtils.formatDate(date, getActivity()));
        tvTime.setText(time);
    }

    @Override
    public void saveDateDuration(String dateDuration) {
        this.mDateDuration = dateDuration;
        tvDateDuration.setText(vn.com.nghiemduong.moneykeeper.utils.AppUtils.formatDate(dateDuration, getActivity()));
        int result = vn.com.nghiemduong.moneykeeper.utils.AppUtils.compareDateWithCurrentDate(dateDuration, getActivity());
        if (result >= 0) { // điều kiện đúng ngày trả nợ phải lớn hơn ngày hiện tại
            tvDateDuration.setTextColor(getResources().getColor(R.color.text_valuable));
        } else {
            tvDateDuration.setTextColor(getResources().getColor(R.color.text_warring));
            showCustomToast("Ngày trả nợ phải lớn hơn hoặc nhỏ hơn bằng ngày "
                    + AppUtils.getDateCurrent(getActivity()), AppConstants.TOAST_WARRING);
        }


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
        ivImageAccount.setImageBitmap(vn.com.nghiemduong.moneykeeper.utils.AppUtils.convertPathFileImageAssetsToBitmap(
                account.getAccountTypePath(), Objects.requireNonNull(getContext())));
        tvTitleAccount.setText(mAccount.getAccountName());
        tvTitleAccount.setTextColor(getResources().getColor(R.color.text_valuable));
    }

    /**
     * Xoá ghi chép
     *
     * @created_by nxduong on 12/3/2021
     */
    @Override
    public void onClickYesDelete() {
        try {
            if (mRecord != null) {
                long delete = new RecordDatabase(getContext()).deleteRecord(mRecord);
                if (delete == DBUtils.checkDBFail) {
                    showCustomToast(getString(R.string.delete) +
                            " " + getString(R.string.fail), AppConstants.TOAST_ERROR);
                } else {
                    showCustomToast(getString(R.string.delete) +
                            " " + getString(R.string.success), AppConstants.TOAST_SUCCESS);
                    onBackPressed();
                }
            }
        } catch (Exception e) {
            vn.com.nghiemduong.moneykeeper.utils.AppUtils.handlerException(e);
        }
    }
}