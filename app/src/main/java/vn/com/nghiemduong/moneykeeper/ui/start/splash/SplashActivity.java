package vn.com.nghiemduong.moneykeeper.ui.start.splash;

import android.content.Intent;
import android.os.Bundle;

import vn.com.nghiemduong.moneykeeper.data.db.CopyMoneyDatabase;
import vn.com.nghiemduong.moneykeeper.ui.base.BaseActivity;
import vn.com.nghiemduong.moneykeeper.ui.start.intro.SplashIntroActivity;

/**
 * - @created_by nxduong on 22/1/2021
 **/

public class SplashActivity extends BaseActivity implements SplashMvpView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startActivity(new Intent(this, SplashIntroActivity.class));
        finish();
    }
}