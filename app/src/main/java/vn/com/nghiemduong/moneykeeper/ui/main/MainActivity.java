package vn.com.nghiemduong.moneykeeper.ui.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import vn.com.nghiemduong.moneykeeper.R;
import vn.com.nghiemduong.moneykeeper.adapter.BottomNavigationAdapter;
import vn.com.nghiemduong.moneykeeper.ui.base.BaseActivity;
import vn.com.nghiemduong.moneykeeper.ui.view.HackyViewPager;

/**
 * -
 * <p>
 * <p>
 * - @created_by nxduong on 21/1/2021
 **/

public class MainActivity extends BaseActivity implements MainMvpView {
    private HackyViewPager hvpMain;
    private MainPresenter mMainPresenter;
    private BottomNavigationAdapter mNavigationAdapter;
    private BottomNavigationView bnvMain;
    private FloatingActionButton fabPlusMain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        bnvMain.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.item_overview:
                        hvpMain.setCurrentItem(0);
                        break;
                    case R.id.item_account:
                        hvpMain.setCurrentItem(1);
                        break;
                    case R.id.item_plus:
                        fabPlusMain.setBackgroundColor(getResources().getColor(R.color.blue));
                        hvpMain.setCurrentItem(2);
                        break;
                    case R.id.item_report:
                        hvpMain.setCurrentItem(3);
                        break;
                    case R.id.item_more:
                        hvpMain.setCurrentItem(4);
                        break;
                }
                return true;
            }
        });
    }

    private void init() {
        hvpMain = findViewById(R.id.hvpMain);
        bnvMain = findViewById(R.id.bnvMain);
        fabPlusMain = findViewById(R.id.fabPlusMain);

        mNavigationAdapter = new BottomNavigationAdapter(getSupportFragmentManager());
        hvpMain.setAdapter(mNavigationAdapter);
    }
}