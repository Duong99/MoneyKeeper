package vn.com.nghiemduong.moneykeeper.ui.main.plus.pay;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import java.io.IOException;
import java.util.Objects;

import vn.com.nghiemduong.moneykeeper.R;
import vn.com.nghiemduong.moneykeeper.data.db.MoneyPay.MoneyPayDatabase;
import vn.com.nghiemduong.moneykeeper.data.db.account.AccountMoneyDatabase;
import vn.com.nghiemduong.moneykeeper.data.model.Account;
import vn.com.nghiemduong.moneykeeper.data.model.Category;
import vn.com.nghiemduong.moneykeeper.data.model.MoneyCollect;
import vn.com.nghiemduong.moneykeeper.data.model.MoneyPay;
import vn.com.nghiemduong.moneykeeper.data.model.SubCategory;
import vn.com.nghiemduong.moneykeeper.ui.base.BaseFragment;
import vn.com.nghiemduong.moneykeeper.ui.dialog.attention.AttentionDialog;
import vn.com.nghiemduong.moneykeeper.ui.main.plus.UtilsPlus;
import vn.com.nghiemduong.moneykeeper.ui.main.plus.chooseaccount.ChooseAccountActivity;
import vn.com.nghiemduong.moneykeeper.ui.main.category.choose.ChooseCategoriesActivity;
import vn.com.nghiemduong.moneykeeper.ui.main.plus.contact.ContactActivity;
import vn.com.nghiemduong.moneykeeper.ui.dialog.date.CustomDateTimeDialog;
import vn.com.nghiemduong.moneykeeper.utils.AppPermission;
import vn.com.nghiemduong.moneykeeper.utils.AppUtils;
import vn.com.nghiemduong.moneykeeper.utils.DBUtils;

import static android.app.Activity.RESULT_OK;

/**
 * -  Màn hình chi tiền
 * <p>
 * - @created_by nxduong on 26/1/2021
 **/
public class PayFragment extends BaseFragment implements PayFragmentMvpView, View.OnClickListener,
        CustomDateTimeDialog.IOnClickSaveDateTime, AttentionDialog.IOnClickAttentionDialog {

    private View mView;
    private RelativeLayout rlChooseCategoryPay, rlChooseAccountPay, rlSelectCategoryFee,
            rlRepaymentDate, rlLender, rlWhoContact, rlSelectFolder, rlSelectCamera,
            rlContentImage;
    private ImageView ivImageCategoriesPay, ivImageAccountPay, ivImageCategoryFee,
            ivImageSelectedPay, ivRemoveImageSelected, ivDetail;

    private TextView tvTitleSelectCategoryPay, tvTitleAccountPay, tvTitleCategoryFee,
            tvTitlePerson, tvTimePay, tvCalenderPay, tvDetail;

    private EditText etInputMoney, etExplain;

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch swtFee, swtLoanToPayAmount, swNotIncludeReport;
    private LinearLayout llContentFee, llContentLoanToPayAmount, llSelectImage, llSave,
            llContentDetail, llLayoutDetail, llDelete;
    private Bitmap imagePay = null;
    private MoneyPayDatabase mMoneyPayDatabase;
    private AccountMoneyDatabase mAccountMoneyDatabase;
    private Account mAccount;
    private Category mCategory;
    private SubCategory mSubCategory;
    private MoneyPay mMoneyPay;
    private PayFragmentPresenter mPayFragmentPresenter;


    public PayFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_pay, container, false);
        init();

        getDataMoneyPayFromBundle();
        mPayFragmentPresenter.doGetAccountFirstFromDB(getContext());
        swtFee.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                extendOrCloseFee(isChecked);
            }
        });

        swtLoanToPayAmount.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                extendOrCloseLoanToPayAmount(isChecked);
            }
        });
        return mView;
    }

    /**
     * Hàm lấy dữ liệu đối tượng MoneyPay khi người dùng click vào để chỉnh sửa hoặc xóa
     * và set các view tương ứng
     *
     * @created_by nxduong on 5/2/2021
     */

    private void getDataMoneyPayFromBundle() {
        if (this.getArguments() != null) {
            mPayFragmentPresenter.doGetMoneyPayFromBundle(this, getContext());
        }
    }

    private void extendOrCloseFee(boolean isChecked) {
        if (isChecked) {
            llContentFee.setVisibility(View.VISIBLE);
        } else {
            llContentFee.setVisibility(View.GONE);
        }
    }

    private void extendOrCloseLoanToPayAmount(boolean isChecked) {
        if (isChecked) {
            llContentLoanToPayAmount.setVisibility(View.VISIBLE);
        } else {
            llContentLoanToPayAmount.setVisibility(View.GONE);
        }
    }

    // khởi tạo / ánh xạ
    private void init() {
        rlChooseCategoryPay = mView.findViewById(R.id.rlChooseCategory);
        rlChooseCategoryPay.setOnClickListener(this);

        rlLender = mView.findViewById(R.id.rlLender);
        rlLender.setOnClickListener(this);

        llSave = mView.findViewById(R.id.llSave);
        llSave.setOnClickListener(this);

        llLayoutDetail = mView.findViewById(R.id.llLayoutDetail);
        llLayoutDetail.setOnClickListener(this);

        llDelete = mView.findViewById(R.id.llDelete);
        llDelete.setOnClickListener(this);

        swtFee = mView.findViewById(R.id.swtFee);
        swtLoanToPayAmount = mView.findViewById(R.id.swtLoanToPayAmount);
        swNotIncludeReport = mView.findViewById(R.id.swNotIncludeReport);

        rlChooseAccountPay = mView.findViewById(R.id.rlChooseAccount);
        rlChooseAccountPay.setOnClickListener(this);

        rlSelectCategoryFee = mView.findViewById(R.id.rlSelectCategoryFee);
        rlSelectCategoryFee.setOnClickListener(this);

        rlRepaymentDate = mView.findViewById(R.id.rlRepaymentDate);
        rlRepaymentDate.setOnClickListener(this);

        rlWhoContact = mView.findViewById(R.id.rlWhoContact);
        rlWhoContact.setOnClickListener(this);

        rlSelectFolder = mView.findViewById(R.id.rlSelectFolder);
        rlSelectFolder.setOnClickListener(this);

        rlSelectCamera = mView.findViewById(R.id.rlSelectCamera);
        rlSelectCamera.setOnClickListener(this);

        tvTitlePerson = mView.findViewById(R.id.tvTitlePerson);
        tvTitlePerson.setText(getResources().getString(R.string.pay_for_someone));

        tvTimePay = mView.findViewById(R.id.tvTime);
        tvTimePay.setOnClickListener(this);
        tvTimePay.setText(UtilsPlus.getTimeCurrent());

        tvCalenderPay = mView.findViewById(R.id.tvCalendar);
        tvCalenderPay.setOnClickListener(this);
        tvCalenderPay.setText(UtilsPlus.getDateCurrent());

        ivImageCategoriesPay = mView.findViewById(R.id.ivImageCategories);
        ivImageSelectedPay = mView.findViewById(R.id.ivImageSelected);
        ivImageAccountPay = mView.findViewById(R.id.ivImageAccount);
        ivImageCategoryFee = mView.findViewById(R.id.ivImageCategoryFee);
        ivDetail = mView.findViewById(R.id.ivDetail);
        tvDetail = mView.findViewById(R.id.tvDetail);

        etInputMoney = mView.findViewById(R.id.etInputMoney);
        etInputMoney.setTextColor(getResources().getColor(R.color.red));

        etExplain = mView.findViewById(R.id.etExplain);

        ivRemoveImageSelected = mView.findViewById(R.id.ivRemoveImageSelected);
        ivRemoveImageSelected.setOnClickListener(this);

        llContentFee = mView.findViewById(R.id.llContentFee);
        llContentLoanToPayAmount = mView.findViewById(R.id.llContentLoanToPayAmount);

        rlContentImage = mView.findViewById(R.id.rlContentImage);
        llSelectImage = mView.findViewById(R.id.llSelectImage);
        llContentDetail = mView.findViewById(R.id.llContentDetail);

        tvTitleSelectCategoryPay = mView.findViewById(R.id.tvTitleSelectCategory);
        tvTitleAccountPay = mView.findViewById(R.id.tvTitleAccount);
        tvTitleCategoryFee = mView.findViewById(R.id.tvTitleCategoryFee);

        mMoneyPayDatabase = new MoneyPayDatabase(getContext());
        mPayFragmentPresenter = new PayFragmentPresenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlChooseCategory: // chọn hạng mục
                try {
                    startActivityForResult(new Intent(getContext(), ChooseCategoriesActivity.class),
                            ChooseCategoriesActivity.REQUEST_CODE_CHOOSE_CATEGORY);
                } catch (Exception e) {
                    AppUtils.handlerException(e);
                }
                break;

            case R.id.rlSelectCategoryFee: // Chọn hạng mục ở mục phí
                try {
                    startActivityForResult(new Intent(getContext(), ChooseCategoriesActivity.class),
                            ChooseCategoriesActivity.REQUEST_CODE_CHOOSE_CATEGORY_FEE);
                } catch (Exception e) {
                    AppUtils.handlerException(e);
                }
                break;

            case R.id.rlChooseAccount: // Chọn tài khoản
                try {
                    Intent intent = new Intent(getContext(), ChooseAccountActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("BUNDLE_ACCOUNT", mAccount);
                    intent.putExtra("BUNDLE", bundle);
                    startActivityForResult(intent,
                            ChooseAccountActivity.REQUEST_CODE_CHOOSE_ACCOUNT);
                } catch (Exception e) {
                    AppUtils.handlerException(e);
                }
                break;

            case R.id.tvTime: // Chọn thời gian
                try {
                    CustomDateTimeDialog customCalendarView = new CustomDateTimeDialog(getContext(),
                            CustomDateTimeDialog.KEY_WATCH, tvCalenderPay.getText().toString(),
                            tvTimePay.getText().toString(), this);
                    customCalendarView.show();
                } catch (Exception e) {
                    AppUtils.handlerException(e);
                }
                break;

            case R.id.tvCalendar: // Chọn ngày tháng năm
                try {
                    CustomDateTimeDialog customCalendarView = new CustomDateTimeDialog(getContext(),
                            CustomDateTimeDialog.KEY_CALENDAR, tvCalenderPay.getText().toString(),
                            tvTimePay.getText().toString(), this);
                    customCalendarView.show();
                } catch (Exception e) {
                    AppUtils.handlerException(e);
                }
                break;

            case R.id.rlWhoContact:
                try {
                    Intent intent = new Intent(getContext(), ContactActivity.class);
                    intent.putExtra(ContactActivity.KEY_CONTACT_ACTIVITY_TYPE,
                            ContactActivity.REQUEST_CODE_CHOOSE_CONTACT_WITH_WHOM);
                    startActivityForResult(intent,
                            ContactActivity.REQUEST_CODE_CHOOSE_CONTACT_WITH_WHOM);
                } catch (Exception e) {
                    AppUtils.handlerException(e);
                }
                break;

            case R.id.rlLender: // Chọn người cho vay
                try {
                    Intent intent = new Intent(getContext(), ContactActivity.class);
                    intent.putExtra(ContactActivity.KEY_CONTACT_ACTIVITY_TYPE,
                            ContactActivity.REQUEST_CODE_CHOOSE_CONTACT_LENDER);
                    startActivityForResult(intent,
                            ContactActivity.REQUEST_CODE_CHOOSE_CONTACT_LENDER);
                } catch (Exception e) {
                    AppUtils.handlerException(e);
                }
                break;

            case R.id.rlRepaymentDate:
                try {

                } catch (Exception e) {
                    AppUtils.handlerException(e);
                }
                break;

            case R.id.rlSelectFolder: // Chọn ảnh từ thu mục
                try {
                    if (AppPermission.requestCameraPermission(getContext(), getActivity())) {
                        Intent intentFolder = new Intent(Intent.ACTION_PICK);
                        intentFolder.setType("image/*");
                        startActivityForResult(intentFolder, AppUtils.REQUEST_CODE_IMAGE_FROM_FOLDER);
                    }
                } catch (Exception e) {
                    AppUtils.handlerException(e);
                }

                break;

            case R.id.rlSelectCamera: // Chọn camera chụp ảnh
                try {
                    if (AppPermission.requestCameraPermission(getContext(), getActivity())) {
                        Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intentCamera, AppUtils.REQUEST_CODE_IMAGE_FROM_CAMERA);
                    }
                } catch (Exception e) {
                    AppUtils.handlerException(e);
                }

                break;

            case R.id.ivRemoveImageSelected: // Xóa ảnh
                try {
                    llSelectImage.setVisibility(View.VISIBLE);
                    rlContentImage.setVisibility(View.GONE);
                    imagePay = null;
                } catch (Exception e) {
                    AppUtils.handlerException(e);
                }
                break;

            case R.id.llSave:
                if (AppUtils.getEditText(etInputMoney).isEmpty()) {
                    etInputMoney.requestFocus();
                } else if (mCategory == null) {

                } else if (mAccount == null) {

                } else {
                    int report = 1;

                    if (swNotIncludeReport.isChecked()) {
                        report = 0;
                    }
                    byte[] image = null;
                    if (imagePay != null) {
                        image = AppUtils.convertBitmapToByteArray(imagePay);
                    }

                    int accountId = mAccount.getAccountId();
                    int amountOfMoney = Integer.parseInt(AppUtils.getEditText(etInputMoney));
                    int subCategoryId;
                    int categoryId;
                    String explain = AppUtils.getEditText(etExplain);
                    String date = tvCalenderPay.getText().toString();
                    String time = tvTimePay.getText().toString();

                    if (mMoneyPay == null) { // Thêm chi tiền
                        MoneyPay moneyPay;
                        if (mSubCategory != null) { // Đối tượng có hạng mục con
                            subCategoryId = mSubCategory.getSubCategoryId();
                            categoryId = mSubCategory.getCategoryId();

                            moneyPay = new MoneyPay(accountId, amountOfMoney,
                                    categoryId, subCategoryId, explain, date, time, report, image);
                        } else { // Đối tượng không có hạng mục con
                            categoryId = mCategory.getCategoryId();
                            moneyPay = new MoneyPay(accountId, amountOfMoney,
                                    categoryId, explain, date, time, report, image);
                        }
                        long insert = mMoneyPayDatabase.insertMoneyPay(moneyPay);
                        if (insert == DBUtils.checkDBFail) {
                            showToast(getResources().getString(R.string.insert_pay_fail));
                        } else {
                            showToast(getResources().getString(R.string.insert_pay_success));
                            etInputMoney.setText(getString(R.string._0));
                            etExplain.setText(null);
                        }
                    } else { // Sửa chi tiền
                        int moneyPrevious = mMoneyPay.getAmountOfMoney();
                        MoneyPay moneyPay;
                        if (mSubCategory != null) { // Đối tượng có hạng mục con
                            subCategoryId = mSubCategory.getSubCategoryId();
                            categoryId = mSubCategory.getCategoryId();

                            moneyPay = new MoneyPay(mMoneyPay.getPayId(),
                                    accountId, amountOfMoney, categoryId, subCategoryId,
                                    explain, date, time, report, image);
                        } else { // Đối tượng không có hạng mục con
                            categoryId = mMoneyPay.getCategoryId();
                            moneyPay = new MoneyPay(mMoneyPay.getPayId(),
                                    accountId, amountOfMoney, categoryId, 0,
                                    explain, date, time, report, image);
                        }

                        long update = mMoneyPayDatabase.updateMoneyPay(moneyPay, moneyPrevious);
                        if (update == DBUtils.checkDBFail) {
                            showToast(getResources().getString(R.string.update_pay_fail));
                        } else {
                            showToast(getResources().getString(R.string.update_pay_success));
                            onBackPressed();
                        }
                    }
                }
                break;

            case R.id.llLayoutDetail:
                if (llContentDetail.getVisibility() == View.GONE) {
                    llContentDetail.setVisibility(View.VISIBLE);
                    tvDetail.setText(getString(R.string.hidden_detail));
                    ivDetail.setBackgroundResource(R.drawable.ic_arrow_up);
                } else {
                    llContentDetail.setVisibility(View.GONE);
                    tvDetail.setText(getString(R.string.more_detail));
                    ivDetail.setBackgroundResource(R.drawable.ic_arrow_down);
                }
                break;

            case R.id.llDelete:
                try {
                    new AttentionDialog(Objects.requireNonNull(getContext()),
                            this, AttentionDialog.ATTENTION_DELETE_DATA).show();
                } catch (Exception e) {
                    AppUtils.handlerException(e);
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (data != null) {
                switch (requestCode) {
                    case ChooseCategoriesActivity.REQUEST_CODE_CHOOSE_CATEGORY_FEE:
                        mCategory = UtilsPlus.onFinishChooseCategory(data,
                                ivImageCategoryFee, tvTitleCategoryFee, getContext());
                        break;

                    case ChooseCategoriesActivity.REQUEST_CODE_CHOOSE_CATEGORY:
                        mPayFragmentPresenter.onActivityResultChooseCategoryPay(data, getContext());
                        break;

                    case ChooseAccountActivity.REQUEST_CODE_CHOOSE_ACCOUNT:
                        mPayFragmentPresenter.onActivityResultAccountPay(data);
                        break;

                    case AppUtils.REQUEST_CODE_IMAGE_FROM_FOLDER:
                        try {
                            Uri uri = data.getData();
                            imagePay = MediaStore.Images.Media.getBitmap(Objects.requireNonNull(getContext())
                                    .getContentResolver(), uri);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        ivImageSelectedPay.setImageBitmap(imagePay);
                        llSelectImage.setVisibility(View.GONE);
                        rlContentImage.setVisibility(View.VISIBLE);
                        break;

                    case AppUtils.REQUEST_CODE_IMAGE_FROM_CAMERA:
                        imagePay = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
                        ivImageSelectedPay.setImageBitmap(imagePay);
                        llSelectImage.setVisibility(View.GONE);
                        rlContentImage.setVisibility(View.VISIBLE);
                        break;
                }
            }
        }
    }

    @Override
    public void saveDateTime(String date, String time) {
        tvCalenderPay.setText(date);
        tvTimePay.setText(time);
    }


    @Override
    public void onClickYesDelete() {
        try {
            if (mMoneyPay != null) {
                long delete = mMoneyPayDatabase.deleteMoneyPay(mMoneyPay);

                if (delete == DBUtils.checkDBFail) {
                    showToast(getString(R.string.delete_pay_fail));
                } else {
                    showToast(getString(R.string.delete_pay_success));
                    mMoneyPay = null;
                    onBackPressed();
                }
            }
        } catch (Exception e) {
            AppUtils.handlerException(e);
        }
    }

    @Override
    public void resultGetMoneyPayFromBundle(MoneyPay moneyPay, Account account,
                                            Category category, SubCategory subCategory) {
        this.mMoneyPay = moneyPay;
        this.mAccount = account;
        this.mCategory = category;
        this.mSubCategory = subCategory;
        if (mMoneyPay != null) {
            etInputMoney.setText(String.valueOf(mMoneyPay.getAmountOfMoney()));
            etExplain.setText(mMoneyPay.getExplain());
            tvCalenderPay.setText(mMoneyPay.getDate());
            tvTimePay.setText(mMoneyPay.getTime());
            if (mMoneyPay.getImage() != null) {
                rlContentImage.setVisibility(View.VISIBLE);
                llSelectImage.setVisibility(View.GONE);
                ivImageSelectedPay.setImageBitmap(
                        AppUtils.convertByteArrayToBitmap(mMoneyPay.getImage()));
            }

            llDelete.setVisibility(View.VISIBLE); // Hiện nút xóa lên

            if (mSubCategory != null) {
                ivImageCategoriesPay.setImageBitmap(
                        AppUtils.convertPathFileImageAssetsToBitmap(mSubCategory.getSubCategoryPath(),
                                Objects.requireNonNull(getContext())));
                tvTitleSelectCategoryPay.setText(mSubCategory.getSubCategoryName());
            } else {
                if (mCategory != null) {
                    ivImageCategoriesPay.setImageBitmap(
                            AppUtils.convertPathFileImageAssetsToBitmap(mCategory.getCategoryPath(),
                                    Objects.requireNonNull(getContext())));
                    tvTitleSelectCategoryPay.setText(mCategory.getCategoryName());
                }
            }

            if (mAccount != null) {
                tvTitleAccountPay.setText(mAccount.getAccountName());
                ivImageAccountPay.setImageBitmap(AppUtils.convertPathFileImageAssetsToBitmap(
                        mAccount.getAccountTypePath(), Objects.requireNonNull(getContext())));
            }
        }
    }

    @Override
    public void resultOnActivityResultChooseCategoryPay(Category category, SubCategory subCategory) {
        this.mCategory = category;
        this.mSubCategory = subCategory;
        if (mSubCategory != null) {
            ivImageCategoriesPay.setImageBitmap(
                    AppUtils.convertPathFileImageAssetsToBitmap(mCategory.getCategoryPath(),
                            Objects.requireNonNull(getContext())));
            tvTitleSelectCategoryPay.setText(mCategory.getCategoryName());
        } else {
            if (mCategory != null) {
                ivImageCategoriesPay.setImageBitmap(
                        AppUtils.convertPathFileImageAssetsToBitmap(mCategory.getCategoryPath(),
                                Objects.requireNonNull(getContext())));
                tvTitleSelectCategoryPay.setText(mCategory.getCategoryName());
            }
        }
    }

    @Override
    public void resultOnFinishChooseAccountPay(Account account) {
        this.mAccount = account;
        if (mAccount != null) {
            ivImageAccountPay.setImageBitmap(AppUtils.convertPathFileImageAssetsToBitmap(
                    mAccount.getAccountTypePath(), Objects.requireNonNull(getContext())));
            tvTitleAccountPay.setText(mAccount.getAccountName());
        }
    }

    @Override
    public void resultGetAccountFirstFromDB(Account account) {
        if (mAccount == null) {
            this.mAccount = account;
            if (mAccount != null) {
                ivImageAccountPay.setImageBitmap(AppUtils.convertPathFileImageAssetsToBitmap(
                        mAccount.getAccountTypePath(), Objects.requireNonNull(getContext())));
                tvTitleAccountPay.setText(mAccount.getAccountName());
            }
        }
    }
}
