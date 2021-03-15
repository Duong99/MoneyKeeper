package vn.com.nghiemduong.moneykeeper.ui.account.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.facebook.CallbackManager;


import vn.com.nghiemduong.moneykeeper.R;
import vn.com.nghiemduong.moneykeeper.data.model.db.User;
import vn.com.nghiemduong.moneykeeper.ui.base.BaseActivity;
import vn.com.nghiemduong.moneykeeper.ui.account.forget.ForgetPasswordActivity;
import vn.com.nghiemduong.moneykeeper.ui.account.register.RegisterActivity;
import vn.com.nghiemduong.moneykeeper.ui.custom.CustomToast;
import vn.com.nghiemduong.moneykeeper.ui.main.MainActivity;
import vn.com.nghiemduong.moneykeeper.utils.AppConstants;
import vn.com.nghiemduong.moneykeeper.utils.AppUtils;

/**
 * -
 * Màn hình đăng nhập ứng dụng
 * <p>
 * - @created_by nxduong on 21/1/2021
 **/

public class LoginActivity extends BaseActivity implements LoginMvpView, View.OnClickListener {

    private LoginPresenter mLoginPresenter;
    private EditText etPasswordLogin, etPhoneOrEmailLogin;
    private CallbackManager mCallbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();


    }

    // Khởi tạo / ánh xạ
    private void init() {
        ImageView ivLoginFacebook = findViewById(R.id.ivLoginFacebook);
        ivLoginFacebook.setOnClickListener(this);

        ImageView ivLoginGoogle = findViewById(R.id.ivLoginGoogle);
        ivLoginGoogle.setOnClickListener(this);

        TextView tvForgetPassword = findViewById(R.id.tvForgetPassword);
        tvForgetPassword.setOnClickListener(this);

        TextView tvRegister = findViewById(R.id.tvRegister);
        tvRegister.setOnClickListener(this);

        Button btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);

        etPasswordLogin = findViewById(R.id.etPasswordLogin);
        etPhoneOrEmailLogin = findViewById(R.id.etPhoneOrEmailLogin);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);

        mLoginPresenter = new LoginPresenter(this, this, this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivLoginFacebook:
                mCallbackManager = CallbackManager.Factory.create();
                mLoginPresenter.loginFacebook(mCallbackManager);
                break;

            case R.id.ivLoginGoogle:
                mLoginPresenter.loginGoogle();
                break;

            case R.id.tvForgetPassword:
                startActivity(new Intent(this, ForgetPasswordActivity.class));
                break;

            case R.id.tvRegister:
                startActivity(new Intent(this, RegisterActivity.class));
                break;

            case R.id.btnLogin:
                String email = AppUtils.getEditText(etPhoneOrEmailLogin);
                String password = AppUtils.getEditText(etPasswordLogin);
                if (email.isEmpty()) {
                    showCustomToast(getString(R.string.please_enter_email), AppConstants.TOAST_WARRING);
                    etPhoneOrEmailLogin.requestFocus();
                } else if (password.isEmpty()) {
                    showCustomToast(getString(R.string.please_enter_password), AppConstants.TOAST_WARRING);
                    etPasswordLogin.requestFocus();
                } else {
                    User user = new User(email, password);
                    mLoginPresenter.loginFirebase(user);
                }
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void loginSuccess() {
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void loginFail(String message) {
        showCustomToast(message, AppConstants.TOAST_ERROR);
    }
}