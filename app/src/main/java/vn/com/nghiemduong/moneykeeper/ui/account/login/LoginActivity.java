package vn.com.nghiemduong.moneykeeper.ui.account.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import com.google.android.gms.tasks.Task;

import java.util.Arrays;

import vn.com.nghiemduong.moneykeeper.R;
import vn.com.nghiemduong.moneykeeper.data.model.db.User;
import vn.com.nghiemduong.moneykeeper.ui.base.BaseActivity;
import vn.com.nghiemduong.moneykeeper.ui.account.forget.ForgetPasswordActivity;
import vn.com.nghiemduong.moneykeeper.ui.account.register.RegisterActivity;
import vn.com.nghiemduong.moneykeeper.ui.main.MainActivity;
import vn.com.nghiemduong.moneykeeper.utils.AppUtils;

/**
 * -
 * Màn hình đăng nhập ứng dụng
 * <p>
 * - @created_by nxduong on 21/1/2021
 **/

public class LoginActivity extends BaseActivity implements LoginMvpView, View.OnClickListener {

    private ImageView ivLoginFacebook, ivLoginGoogle;
    private TextView tvForgetPassword, tvRegister;
    private Button btnLogin;
    private LoginPresenter mLoginPresenter;
    private LoginButton lbLoginFacebook;
    private EditText etPasswordLogin, etPhoneOrEmailLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();

        CallbackManager callbackManager = CallbackManager.Factory.create();

        lbLoginFacebook.setReadPermissions(Arrays.asList(LoginPresenter.EMAIL));
        lbLoginFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    // Khởi tạo / ánh xạ
    private void init() {
        lbLoginFacebook = findViewById(R.id.lbLoginFacebook);
        //ivLoginFacebook.setOnClickListener(this);

        ivLoginGoogle = findViewById(R.id.ivLoginGoogle);
        ivLoginGoogle.setOnClickListener(this);

        tvForgetPassword = findViewById(R.id.tvForgetPassword);
        tvForgetPassword.setOnClickListener(this);

        tvRegister = findViewById(R.id.tvRegister);
        tvRegister.setOnClickListener(this);

        btnLogin = findViewById(R.id.btnLogin);
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
                mLoginPresenter.loginFacebook(ivLoginFacebook);

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
                if (AppUtils.getEditText(etPhoneOrEmailLogin).isEmpty()) {
                    etPhoneOrEmailLogin.requestFocus();
                } else if (AppUtils.getEditText(etPasswordLogin).isEmpty()) {
                    etPasswordLogin.requestFocus();
                } else {
                    User user = new User(AppUtils.getEditText(etPhoneOrEmailLogin),
                            AppUtils.getEditText(etPasswordLogin));
                    mLoginPresenter.loginFirebase(user);
                }
                break;
        }
    }


    @Override
    public void loginGoogleSuccess() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
    }

    @Override
    public void loginGoogleFail() {

    }

    @Override
    public void loginFacebookSuccess() {

    }

    @Override
    public void loginFacebookFail() {

    }

    @Override
    public void loginFirebaseSuccess() {
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void loginFirebaseFail() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LoginPresenter.RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            mLoginPresenter.handleSignInGoogleResult(task);
        }
    }
}