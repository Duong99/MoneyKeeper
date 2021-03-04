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
import vn.com.nghiemduong.moneykeeper.ui.base.BaseActivity;
import vn.com.nghiemduong.moneykeeper.ui.main.MainActivity;
import vn.com.nghiemduong.moneykeeper.utils.AppUtils;

/**
 * -  Màn hình đăng ký tài khoản
 * <p>
 * - @created_by nxduong on 22/1/2021
 **/

public class RegisterActivity extends BaseActivity implements RegisterMvpView, View.OnClickListener {

    private TextView tvPolicyRegister, tvLoginNowRegister;
    private Button btnRegister;
    private EditText etLastNameRegister, etFirstNameRegister, etEmailRegister,
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

        etLastNameRegister = findViewById(R.id.etLastNameRegister);
        etFirstNameRegister = findViewById(R.id.etFirstNameRegister);
        etEmailRegister = findViewById(R.id.etEmailRegister);
        etPasswordRegister = findViewById(R.id.etPasswordRegister);
        etNumberPhoneRegister = findViewById(R.id.etNumberPhoneRegister);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRegister:
                if (AppUtils.getEditText(etLastNameRegister).isEmpty()) {
                    etLastNameRegister.requestFocus();
                } else if (AppUtils.getEditText(etFirstNameRegister).isEmpty()) {
                    etFirstNameRegister.requestFocus();
                } else if (AppUtils.getEditText(etEmailRegister).isEmpty()) {
                    etEmailRegister.requestFocus();
                } else if (AppUtils.getEditText(etNumberPhoneRegister).isEmpty()) {
                    etNumberPhoneRegister.requestFocus();
                } else if (AppUtils.getEditText(etPasswordRegister).isEmpty()) {
                    etPasswordRegister.requestFocus();
                } else {
                    User user = null;
                    mRegisterPresenter = new RegisterPresenter(this, this);
                    mRegisterPresenter.registerAccount(user);
                }
                break;
        }
    }

    @Override
    public void registerAccountSuccess() {
        showToast("Đăng nhập thành công");
        // Đăng ký tài khoản thành công chuyển sang màn hình chính
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void registerAccountFail() {
        showToast("Đăng nhập thất bại");
    }
}