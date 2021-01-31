package vn.com.nghiemduong.moneykeeper.ui.account.forget;

import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;

import vn.com.nghiemduong.moneykeeper.R;
import vn.com.nghiemduong.moneykeeper.ui.base.BaseActivity;

/**
 * - Màn hình quên mật khẩu tài khoản
 * <p>
 * <p>
 * - @created_by nxduong on 22/1/2021
 **/

public class ForgetPasswordActivity extends BaseActivity implements ForgetPasswordMvpView {

    private Toolbar tbaRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        init();

        onClickView();
    }

    private void onClickView() {
        tbaRegister.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    // Khởi tạo / ánh xạ
    private void init() {
        tbaRegister = findViewById(R.id.tbaRegister);
    }
}