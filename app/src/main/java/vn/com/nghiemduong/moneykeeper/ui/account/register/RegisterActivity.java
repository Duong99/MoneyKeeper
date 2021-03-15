package vn.com.nghiemduong.moneykeeper.ui.account.register;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import vn.com.nghiemduong.moneykeeper.R;
import vn.com.nghiemduong.moneykeeper.data.model.db.User;
import vn.com.nghiemduong.moneykeeper.ui.account.login.LoginActivity;
import vn.com.nghiemduong.moneykeeper.ui.base.BaseActivity;
import vn.com.nghiemduong.moneykeeper.ui.main.MainActivity;
import vn.com.nghiemduong.moneykeeper.utils.AppConstants;
import vn.com.nghiemduong.moneykeeper.utils.AppUtils;

/**
 * -  Màn hình đăng ký tài khoản
 * <p>
 * - @created_by nxduong on 22/1/2021
 **/

public class RegisterActivity extends BaseActivity implements RegisterMvpView, View.OnClickListener {

    private TextView tvPolicyRegister, tvLoginNowRegister;
    private Button btnRegister;
    private EditText etFirstNameRegister, etEmailRegister,
            etPasswordRegister, etNumberPhoneRegister;
    private RegisterPresenter mRegisterPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();

        setTextHtml();
    }

    private void setTextHtml() {
        String text = " Bạn đã có tài khoản? <font color:'#111'> Đăng nhập ngay </font>";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tvLoginNowRegister.setText(Html.fromHtml(text,
                    Html.FROM_HTML_MODE_COMPACT));
        } else {
            tvLoginNowRegister.setText(Html.fromHtml(text));
        }
    }

    private void init() {
        tvPolicyRegister = findViewById(R.id.tvPolicyRegister);
        tvLoginNowRegister = findViewById(R.id.tvLoginNowRegister);

        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);

        etFirstNameRegister = findViewById(R.id.etFirstNameRegister);
        etEmailRegister = findViewById(R.id.etEmailRegister);
        etPasswordRegister = findViewById(R.id.etPasswordRegister);
        etNumberPhoneRegister = findViewById(R.id.etNumberPhoneRegister);

        mRegisterPresenter = new RegisterPresenter(this, this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRegister:
                String name = AppUtils.getEditText(etFirstNameRegister);
                String email = AppUtils.getEditText(etEmailRegister);
                String phone = AppUtils.getEditText(etNumberPhoneRegister);
                String password = AppUtils.getEditText(etPasswordRegister);
                if (name.isEmpty()) {
                    showCustomToast(getString(R.string.please_enter_name), AppConstants.TOAST_WARRING);
                    etFirstNameRegister.requestFocus();
                } else if (email.isEmpty()) {
                    showCustomToast(getString(R.string.please_enter_email), AppConstants.TOAST_WARRING);
                    etEmailRegister.requestFocus();
                } else if (phone.isEmpty()) {
                    showCustomToast(getString(R.string.please_enter_phone), AppConstants.TOAST_WARRING);
                    etNumberPhoneRegister.requestFocus();
                } else if (password.isEmpty()) {
                    showCustomToast(getString(R.string.please_enter_password), AppConstants.TOAST_WARRING);
                    etPasswordRegister.requestFocus();
                } else {
                    User user = new User(name, email, phone, "", AppConstants.NAM, password);
                    mRegisterPresenter.registerAccount(user);
                }
                break;
        }
    }

    @Override
    public void registerAccountSuccess(String message) {
        showCustomToast(message, AppConstants.TOAST_SUCCESS);
        startActivity(new Intent(this, LoginActivity.class));
    }

    @Override
    public void registerAccountFail(String message) {
        showCustomToast(message, AppConstants.TOAST_WARRING);
    }
}