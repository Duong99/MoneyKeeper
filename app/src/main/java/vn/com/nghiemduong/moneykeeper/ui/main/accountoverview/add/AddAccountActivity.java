package vn.com.nghiemduong.moneykeeper.ui.main.accountoverview.add;

import androidx.annotation.Nullable;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Objects;

import vn.com.nghiemduong.moneykeeper.R;
import vn.com.nghiemduong.moneykeeper.data.db.account.AccountDatabase;
import vn.com.nghiemduong.moneykeeper.data.model.db.Account;
import vn.com.nghiemduong.moneykeeper.data.model.AccountType;
import vn.com.nghiemduong.moneykeeper.ui.base.BaseActivity;
import vn.com.nghiemduong.moneykeeper.ui.dialog.attention.AttentionDeleteDialog;
import vn.com.nghiemduong.moneykeeper.ui.main.accountoverview.type.AccountTypeActivity;
import vn.com.nghiemduong.moneykeeper.utils.AppConstants;
import vn.com.nghiemduong.moneykeeper.utils.AppUtils;
import vn.com.nghiemduong.moneykeeper.utils.DBUtils;

/**
 * -
 * Màn hình dùng để thêm tài khoản, sửa tài khoản
 * <p>
 * - @created_by nxduong on 26/1/2021
 **/
public class AddAccountActivity extends BaseActivity implements View.OnClickListener,
        AttentionDeleteDialog.IOnClickAttentionDialog, AddAccountActivityMvpView {

    public final static int REQUEST_CODE_ACCOUNT_ADD = 321;
    public final static int REQUEST_CODE_ACCOUNT_EDIT = 121;

    private EditText etInputAmount, etDescription, etAccountName;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch swNotIncludeReport;
    private ImageView ivImageAccountType;
    private TextView tvTitleAccountType, tvTitleBarAddAccount;
    private LinearLayout llDelete;
    private AccountType mAccountType;
    private AccountDatabase mAccountDatabase;
    private Account mAccount;
    private AddAccountActivityPresenter mAddAccountActivityPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);

        init();
    }

    private void init() {
        ImageView ivCloseAddAccount = findViewById(R.id.ivCloseAddAccount);
        ivCloseAddAccount.setOnClickListener(this);

        ImageView ivDoneAddAccount = findViewById(R.id.ivDoneAddAccount);
        ivDoneAddAccount.setOnClickListener(this);

        RelativeLayout rlSelectMoneyType = findViewById(R.id.rlSelectMoneyType);
        rlSelectMoneyType.setOnClickListener(this);

        RelativeLayout rlSelectAccountType = findViewById(R.id.rlSelectAccountType);
        rlSelectAccountType.setOnClickListener(this);

        LinearLayout llSave = findViewById(R.id.llSave);
        llSave.setOnClickListener(this);

        llDelete = findViewById(R.id.llDelete);
        llDelete.setOnClickListener(this);

        etInputAmount = findViewById(R.id.etInputAmount);
        AppUtils.formatNumberEditText(etInputAmount);
        etInputAmount.setTextColor(getResources().getColor(R.color.blue));

        tvTitleBarAddAccount = findViewById(R.id.tvTitleBarAddAccount);
        etDescription = findViewById(R.id.etDescription);

        AppUtils.addTextChangeEditText(etDescription);

        swNotIncludeReport = findViewById(R.id.swNotIncludeReport);
        ivImageAccountType = findViewById(R.id.ivAccountType);
        tvTitleAccountType = findViewById(R.id.tvAccountType);
        etAccountName = findViewById(R.id.etAccountName);
        mAccountDatabase = new AccountDatabase(this);

        mAddAccountActivityPresenter = new AddAccountActivityPresenter(this);
        mAddAccountActivityPresenter.getAccountBundle(this);
    }

    private void setValueAccountType() {
        ivImageAccountType.setImageBitmap(
                AppUtils.convertPathFileImageAssetsToBitmap(mAccountType.getAccountTypePath(), this));
        tvTitleAccountType.setText(mAccountType.getAccountTypeName());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivCloseAddAccount: // Đóng thêm tài khoản
                try {
                    onBackPressed();
                } catch (Exception e) {
                    AppUtils.handlerException(e);
                }

                break;

            case R.id.ivDoneAddAccount:
            case R.id.llSave: // Thêm tài khoản
                try {
                    insertOrUpdateAccount();
                } catch (Exception e) {
                    AppUtils.handlerException(e);
                }
                break;

            case R.id.rlSelectAccountType: // Chọn loại tài khoản
                try {
                    Intent intentAccountType = new Intent(this, AccountTypeActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("BUNDLE_ACCOUNT_TYPE", mAccountType);
                    intentAccountType.putExtra("BUNDLE", bundle);
                    startActivityForResult(intentAccountType,
                            AccountTypeActivity.REQUEST_CODE_ACCOUNT_TYPE);
                } catch (Exception e) {
                    AppUtils.handlerException(e);
                }
                break;

            case R.id.llDelete: // Xóa tài khoản
                try {
                    if (mAccount != null) {
                        new AttentionDeleteDialog(this, this,
                                AttentionDeleteDialog.ATTENTION_DELETE_ACCOUNT).show();

                    }
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
            if (requestCode == AccountTypeActivity.REQUEST_CODE_ACCOUNT_TYPE) {
                if (data != null) {
                    mAccountType = (AccountType)
                            Objects.requireNonNull(data.getBundleExtra("BUNDLE"))
                                    .getSerializable("BUNDLE_ACCOUNT_TYPE");
                    setValueAccountType();
                }
            }
        }
    }

    private void insertOrUpdateAccount() {
        if (AppUtils.getEditText(etInputAmount).isEmpty()) {
            showCustomToast(getResources().getString(R.string.enter_money),
                    AppConstants.TOAST_WARRING);
            etInputAmount.requestFocus();
        } else if (AppUtils.getEditText(etAccountName).isEmpty()) {
            showCustomToast(getResources().getString(R.string.enter_account_name),
                    AppConstants.TOAST_WARRING);
            etAccountName.requestFocus();
        } else {
            int report;
            if (swNotIncludeReport.isChecked()) {
                report = AppConstants.KHONG_BAO_CAO;
            } else {
                report = AppConstants.CO_BAO_CAO;
            }

            // Thêm tài khoản
            if (mAccount == null) {
                Account account = new Account(AppUtils.getEditText(etAccountName),
                        Integer.parseInt(AppUtils.getEditTextFormatNumber(etInputAmount)),
                        mAccountType.getAccountTypePath(),
                        mAccountType.getAccountTypeName(), AppConstants.VND,
                        AppUtils.getEditText(etDescription), report);
                long insert = mAccountDatabase.insertAccount(account);
                if (insert == DBUtils.checkDBFail) {
                    showCustomToast(getResources().getString(R.string.insert_account_fail), AppConstants.TOAST_ERROR);
                } else {
                    showCustomToast(getResources().getString(R.string.insert_account_success), AppConstants.TOAST_SUCCESS);
                    finishInsertOrUpdateSuccess();
                }
            } else { // Sửa tài khoản
                mAccount = new Account(
                        mAccount.getAccountId(),
                        AppUtils.getEditText(etAccountName),
                        Integer.parseInt(AppUtils.getEditTextFormatNumber(etInputAmount)),
                        mAccountType.getAccountTypePath(),
                        mAccountType.getAccountTypeName(), AppConstants.VND,
                        AppUtils.getEditText(etDescription), report);
                long update = mAccountDatabase.updateAccount(mAccount);
                if (update == DBUtils.checkDBFail) {
                    showToast(getResources().getString(R.string.update_account_fail));
                } else {
                    showToast(getResources().getString(R.string.update_account_success));
                    finishInsertOrUpdateSuccess();
                }
            }
        }
    }

    private void finishInsertOrUpdateSuccess() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("BUNDLE_ACCOUNT", mAccount);
        intent.putExtra("BUNDLE", bundle);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onClickYesDelete() {
        try {
            long delete = mAccountDatabase.deleteAccount(mAccount.getAccountId());

            if (delete == DBUtils.checkDBFail) {
                showToast(getResources().getString(R.string.delete_account_fail));
            } else {
                showCustomToast(getResources().getString(R.string.delete_account_success),
                        AppConstants.TOAST_SUCCESS);
                finishInsertOrUpdateSuccess();
            }
        } catch (Exception e) {
            AppUtils.handlerException(e);
        }
    }

    @Override
    public void resultGetAccountBundle(Account account) {
        this.mAccount = account;
        if (mAccount == null) { // Màn hình thêm tài khoản
            tvTitleBarAddAccount.setText(getString(R.string.add_account));

            mAccountType = new AccountType("assets/ImageCategory/THU/THU_luong.png",
                    getResources().getString(R.string.cash));
            setValueAccountType();
        } else { // Màn hình sửa tài khoản
            tvTitleBarAddAccount.setText(getString(R.string.edit_account));
            etInputAmount.setText(AppUtils.formatNumber(String.valueOf(mAccount.getCurrentAmount())));
            etAccountName.setText(mAccount.getAccountName());
            etDescription.setText(mAccount.getDescription());
            mAccountType = new AccountType(mAccount.getAccountTypePath(),
                    mAccount.getAccountTypeName());

            llDelete.setVisibility(View.VISIBLE);

            if (mAccount.getReport() == AppConstants.CO_BAO_CAO) {
                swNotIncludeReport.setChecked(false);
            } else {
                swNotIncludeReport.setChecked(true);
            }
            setValueAccountType();
        }
    }
}