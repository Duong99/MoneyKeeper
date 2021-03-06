package vn.com.nghiemduong.moneykeeper.ui.main;

import androidx.annotation.NonNull;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import vn.com.nghiemduong.moneykeeper.R;
import vn.com.nghiemduong.moneykeeper.adapter.BottomNavigationAdapter;
import vn.com.nghiemduong.moneykeeper.data.db.account.AccountDatabase;
import vn.com.nghiemduong.moneykeeper.data.model.db.Account;
import vn.com.nghiemduong.moneykeeper.ui.base.BaseActivity;
import vn.com.nghiemduong.moneykeeper.ui.view.HackyViewPager;
import vn.com.nghiemduong.moneykeeper.utils.AppUtils;

/**
 * - @created_by nxduong on 21/1/2021
 **/

public class MainActivity extends BaseActivity implements MainMvpView, View.OnClickListener {
    private HackyViewPager hvpMain;
    private BottomNavigationView bnvMain;
    private FloatingActionButton fabPlusMain;
    private AccountDatabase mAccountMoneyDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        bnvMain.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.item_overview:
                                try {
                                    fabPlusMain.setBackgroundTintList(ColorStateList.valueOf(Color.GRAY));
                                    hvpMain.setCurrentItem(0);
                                } catch (Exception e) {
                                    AppUtils.handlerException(e);
                                }
                                break;
                            case R.id.item_account:
                                try {
                                    fabPlusMain.setBackgroundTintList(ColorStateList.valueOf(Color.GRAY));
                                    hvpMain.setCurrentItem(1);
                                } catch (Exception e) {
                                    AppUtils.handlerException(e);
                                }
                                break;
                            case R.id.item_plus:
                                try {
                                    fabPlusMain.setBackgroundTintList(ColorStateList.valueOf(
                                            getResources().getColor(R.color.blue)));
                                    hvpMain.setCurrentItem(2);
                                } catch (Exception e) {
                                    AppUtils.handlerException(e);
                                }
                                break;
                            case R.id.item_report:
                                try {
                                    fabPlusMain.setBackgroundTintList(ColorStateList.valueOf(Color.GRAY));
                                    hvpMain.setCurrentItem(3);
                                } catch (Exception e) {
                                    AppUtils.handlerException(e);
                                }
                                break;
                            case R.id.item_more:
                                try {
                                    fabPlusMain.setBackgroundTintList(ColorStateList.valueOf(Color.GRAY));
                                    hvpMain.setCurrentItem(4);
                                } catch (Exception e) {
                                    AppUtils.handlerException(e);
                                }
                                break;
                        }
                        return true;
                    }
                });
    }

    // Khởi tạo / Ánh xạ view
    private void init() {
        hvpMain = findViewById(R.id.hvpMain);

        bnvMain = findViewById(R.id.bnvMain);
        fabPlusMain = findViewById(R.id.fabPlusMain);
        fabPlusMain.setOnClickListener(this);
        fabPlusMain.setBackgroundTintList(ColorStateList.valueOf(Color.GRAY));

        BottomNavigationAdapter mNavigationAdapter = new BottomNavigationAdapter(getSupportFragmentManager());
        hvpMain.setAdapter(mNavigationAdapter);

        mAccountMoneyDatabase = new AccountDatabase(this);
    }

    public ArrayList<Account> getAllAccount() {
        return mAccountMoneyDatabase.getAllAccount();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fabPlusMain:
                try {

                    fabPlusMain.setBackgroundTintList(ColorStateList.valueOf(
                            getResources().getColor(R.color.blue)));
                    hvpMain.setCurrentItem(2, true);
                } catch (Exception e) {
                    AppUtils.handlerException(e);
                }
                break;
        }
    }
}