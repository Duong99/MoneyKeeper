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

import java.util.ArrayList;
import java.util.Objects;

import vn.com.nghiemduong.moneykeeper.R;
import vn.com.nghiemduong.moneykeeper.data.db.account.AccountMoneyDatabase;
import vn.com.nghiemduong.moneykeeper.data.model.Account;
import vn.com.nghiemduong.moneykeeper.data.model.AccountType;
import vn.com.nghiemduong.moneykeeper.ui.base.BaseActivity;
import vn.com.nghiemduong.moneykeeper.ui.main.accountoverview.type.AccountTypeActivity;
import vn.com.nghiemduong.moneykeeper.utils.AppUtils;
import vn.com.nghiemduong.moneykeeper.utils.DBUtils;

/**
 * -
 * Màn hình dùng để thêm tài khoản, sửa tài khoản
 * <p>
 * - @created_by nxduong on 26/1/2021
 **/
public class AddAccountActivity extends BaseActivity implements View.OnClickListener {

    public final static int REQUEST_CODE_ACCOUNT_ADD = 321;
    public final static int REQUEST_CODE_ACCOUNT_EDIT = 121;
    public final static int ADD_ACCOUNT = 0;
    public final static int EDIT_ACCOUNT = 1;
    public final static String KEY_ADD_EDIT_ACCOUNT = "KEY_ADD_EDIT_ACCOUNT";

    private ImageView ivCloseAddAccount, ivDoneAddAccount;
    private EditText etInputMoney, etExplain, etAccountName;
    private RelativeLayout rlSelectMoneyType, rlSelectAccountType;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch swNotIncludeReport;
    private LinearLayout llSave;
    private ImageView ivImageAccountType;
    private TextView tvTitleAccountType, tvTitleBarAddAccount;
    private AccountType mAccountType;
    private AccountMoneyDatabase mAccountDatabase;
    private Account mAccount;
    private int keyAddEditAccount = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);

        init();
    }

    private void init() {
        ivCloseAddAccount = findViewById(R.id.ivCloseAddAccount);
        ivCloseAddAccount.setOnClickListener(this);

        ivDoneAddAccount = findViewById(R.id.ivDoneAddAccount);
        ivDoneAddAccount.setOnClickListener(this);

        rlSelectMoneyType = findViewById(R.id.rlSelectMoneyType);
        rlSelectMoneyType.setOnClickListener(this);

        rlSelectAccountType = findViewById(R.id.rlSelectAccountType);
        rlSelectAccountType.setOnClickListener(this);

        llSave = findViewById(R.id.llSave);
        llSave.setOnClickListener(this);

        etInputMoney = findViewById(R.id.etInputMoney);
        tvTitleBarAddAccount = findViewById(R.id.tvTitleBarAddAccount);
        etExplain = findViewById(R.id.etExplain);
        swNotIncludeReport = findViewById(R.id.swNotIncludeReport);
        ivImageAccountType = findViewById(R.id.ivAccountType);
        tvTitleAccountType = findViewById(R.id.tvAccountType);
        etAccountName = findViewById(R.id.etAccountName);
        mAccountDatabase = new AccountMoneyDatabase(this);

        keyAddEditAccount = getIntent().getIntExtra(KEY_ADD_EDIT_ACCOUNT, -1);
        if (keyAddEditAccount == ADD_ACCOUNT) {
            tvTitleBarAddAccount.setText(getString(R.string.add_account));

            mAccountType = new AccountType("assets/ImageCategory/THU/THU_luong.png",
                    getResources().getString(R.string.cash));
            setValueAccountType();

        } else {
            tvTitleBarAddAccount.setText(getString(R.string.edit_account));
            mAccount = (Account) Objects.requireNonNull(getIntent().getBundleExtra("BUNDLE"))
                    .getSerializable("BUNDLE_ACCOUNT");

            if (mAccount != null) {
                etInputMoney.setText(String.valueOf(mAccount.getMoneyCurrent()));
                etAccountName.setText(mAccount.getAccountName());
                etExplain.setText(mAccount.getExplain());
                mAccountType = new AccountType(mAccount.getAccountTypePath(),
                        mAccount.getAccountTypeName());

                if (mAccount.getReport() == AppUtils.CO_BAO_CAO) {
                    swNotIncludeReport.setChecked(false);
                } else {
                    swNotIncludeReport.setChecked(true);
                }
                setValueAccountType();
            }
        }
    }

    private void setValueAccountType() {
        ivImageAccountType.setImageBitmap(
                AppUtils.convertPathFileImageAssetsToBitmap(mAccountType.getAccountTypePath(), this));
        tvTitleAccountType.setText(mAccountType.getAccountTypeName());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivCloseAddAccount:
                try {
                    onBackPressed();
                } catch (Exception e) {
                    AppUtils.handlerException(e);
                }

                break;

            case R.id.ivDoneAddAccount:

            case R.id.llSave:
                try {
                    insertOrUpdateAccount();
                } catch (Exception e) {
                    AppUtils.handlerException(e);
                }
                break;

            case R.id.rlSelectAccountType:
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
        if (AppUtils.getEditText(etInputMoney).isEmpty()) {
            etInputMoney.requestFocus();
        } else if (AppUtils.getEditText(etAccountName).isEmpty()) {
            etAccountName.requestFocus();
        } else {
            int report;
            if (swNotIncludeReport.isChecked()) {
                report = AppUtils.KHONG_BAO_CAO;
            } else {
                report = AppUtils.CO_BAO_CAO;
            }

            // Thêm tài khoản
            if (keyAddEditAccount == ADD_ACCOUNT) {
                mAccount = new Account(AppUtils.getEditText(etAccountName),
                        Integer.parseInt(AppUtils.getEditText(etInputMoney)),
                        mAccountType.getAccountTypePath(),
                        mAccountType.getAccountTypeName(), AppUtils.VND,
                        AppUtils.getEditText(etExplain), report);
                long insert = mAccountDatabase.insertAccount(mAccount);
                if (insert == DBUtils.checkDBFail) {
                    showToast(getResources().getString(R.string.insert_account_fail));
                    finish();
                } else {
                    showToast(getResources().getString(R.string.insert_account_success));
                    finishInsertOrUpdateSuccess();
                }
            } else { // Sửa tài khoản
                mAccount = new Account(
                        mAccount.getAccountId(),
                        AppUtils.getEditText(etAccountName),
                        Integer.parseInt(AppUtils.getEditText(etInputMoney)),
                        mAccountType.getAccountTypePath(),
                        mAccountType.getAccountTypeName(), AppUtils.VND,
                        AppUtils.getEditText(etExplain), report);
                long update = mAccountDatabase.updateAccount(mAccount);
                if (update == DBUtils.checkDBFail) {
                    showToast(getResources().getString(R.string.update_account_fail));
                    finish();
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
}