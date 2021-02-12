package vn.com.nghiemduong.moneykeeper.ui.base;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import vn.com.nghiemduong.moneykeeper.ui.custom.CustomToast;

/**
 * - @created_by nxduong on 21/1/2021
 **/
public class BaseActivity extends AppCompatActivity implements MvpView {

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showCustomToast(String message, int type) {
        CustomToast.makeToast(this, message, type).show();
    }
}
