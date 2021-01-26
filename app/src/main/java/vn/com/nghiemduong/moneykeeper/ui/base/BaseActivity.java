package vn.com.nghiemduong.moneykeeper.ui.base;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 *
 - @created_by nxduong on 21/1/2021

**/
public class BaseActivity extends AppCompatActivity implements MvpView{

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
