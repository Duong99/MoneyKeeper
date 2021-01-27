package vn.com.nghiemduong.moneykeeper.ui.main.accountoverview.add;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;

import vn.com.nghiemduong.moneykeeper.R;
import vn.com.nghiemduong.moneykeeper.data.db.account.AccountMoneyDatabase;
import vn.com.nghiemduong.moneykeeper.data.db.account.AccountMoneyDatabaseMvpView;
import vn.com.nghiemduong.moneykeeper.data.model.Account;
import vn.com.nghiemduong.moneykeeper.data.model.AccountType;
import vn.com.nghiemduong.moneykeeper.ui.base.BaseActivity;
import vn.com.nghiemduong.moneykeeper.ui.main.accountoverview.type.AccountTypeActivity;
import vn.com.nghiemduong.moneykeeper.utils.AppUtils;

/**
 * -
 * Màn hình dùng để thêm tài khoản
 * <p>
 * - @created_by nxduong on 26/1/2021
 **/
public class AddAccountActivity extends BaseActivity implements View.OnClickListener,
        AccountMoneyDatabaseMvpView {

    public final static int REQUEST_CODE_ACCOUNT_ADD = 321;

    private ImageView ivCloseAddAccount, ivDoneAddAccount;
    private EditText etInputMoney, etExplain, etAccountName;
    private RelativeLayout rlSelectMoneyType, rlSelectAccountType;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch swNotIncludeReport;
    private LinearLayout llSave;
    private ImageView ivImageAccountType;
    private TextView tvTitleAccountType;
    private AccountType mAccountType;
    private AccountMoneyDatabase mAccountDatabase;
    private Account mAccount;

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
        etExplain = findViewById(R.id.etExplain);
        swNotIncludeReport = findViewById(R.id.swNotIncludeReport);
        ivImageAccountType = findViewById(R.id.ivAccountType);
        tvTitleAccountType = findViewById(R.id.tvAccountType);
        etAccountName = findViewById(R.id.etAccountName);

        mAccountDatabase = new AccountMoneyDatabase(this, this);

        InputStream is = null;
        try {
            is = getAssets().open("assets/ImageCategory/THU/THU_luong.png");
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (is != null) {
            Bitmap bitmap = BitmapFactory.decodeStream(is);

            mAccountType = new AccountType(AppUtils.TIEN_MAT,
                    AppUtils.convertBitmapToByteArray(bitmap), getResources().getString(R.string.cash));
            setValueAccountType();
        }
    }

    private void setValueAccountType() {
        ivImageAccountType.setImageBitmap(AppUtils.convertByteArrayToBitmap(mAccountType.getImage()));
        tvTitleAccountType.setText(mAccountType.getTitle());
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
                    insertAccount();
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

    private void insertAccount() {
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
            mAccount = new Account(Integer.parseInt(AppUtils.getEditText(etInputMoney)),
                    AppUtils.getEditText(etAccountName), mAccountType.getAccountTypeId(),
                    mAccountType.getImage(), AppUtils.VND, AppUtils.getEditText(etExplain), report);

            mAccountDatabase.insertAccount(mAccount);
        }
    }

    @Override
    public void getAllAccountResult(ArrayList<Account> listAccount) {

    }

    @Override
    public void insertAccountSuccess() {
        showToast(getResources().getString(R.string.insert_account_success));
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("BUNDLE_ACCOUNT", mAccount);
        intent.putExtra("BUNDLE", bundle);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void insertAccountFail() {
        showToast(getResources().getString(R.string.insert_account_fail));
        finish();
    }

    @Override
    public void updateAccountSuccess() {

    }

    @Override
    public void updateAccountFail() {

    }

    @Override
    public void deleteAccountSuccess() {

    }

    @Override
    public void deleteAccountFail() {

    }
}