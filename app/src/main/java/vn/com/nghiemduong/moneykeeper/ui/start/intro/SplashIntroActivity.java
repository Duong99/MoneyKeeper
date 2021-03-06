package vn.com.nghiemduong.moneykeeper.ui.start.intro;

import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import vn.com.nghiemduong.moneykeeper.R;
import vn.com.nghiemduong.moneykeeper.adapter.ViewPagerIntroAdapter;
import vn.com.nghiemduong.moneykeeper.data.db.CopyMoneyDatabase;
import vn.com.nghiemduong.moneykeeper.data.model.Intro;
import vn.com.nghiemduong.moneykeeper.ui.account.register.RegisterActivity;
import vn.com.nghiemduong.moneykeeper.ui.base.BaseActivity;

import vn.com.nghiemduong.moneykeeper.ui.account.login.LoginActivity;
import vn.com.nghiemduong.moneykeeper.ui.main.MainActivity;

/**
 * -
 * <p>
 * <p>
 * - @created_by nxduong on 22/1/2021
 **/

public class SplashIntroActivity extends BaseActivity implements View.OnClickListener,
        SplashIntroMvpView {

    private LinearLayout llDotsSplashIntro;
    private Button btnRegisterSplashIntro;
    private TextView tvLoginSplashIntro;
    private ViewPager vpIntroSplashIntro;
    private ViewPagerIntroAdapter mIntroAdapter;
    private SplashIntroPresenter mSplashIntroPresenter;
    private ImageView[] ivDots;
    private int mDotCount;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_intro);

        init();
        createDots();

        vpIntroSplashIntro.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < mDotCount; i++) {
                    if (i == position) {
                        ivDots[i].setImageDrawable(
                                getResources().getDrawable(R.drawable.dot_slide_selected));
                    } else {
                        ivDots[i].setImageDrawable(
                                getResources().getDrawable(R.drawable.dot_slide_unselect));
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Kiểm tra xem đã đăng nhập người dùng chưa
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) { // Nếu người dùng đang nhập rồi chuyển sang màn hình chính
            startActivity(new Intent(SplashIntroActivity.this, MainActivity.class));
        } else {
            new CopyMoneyDatabase(this); // Tạo database và copy
        }
    }

    /**
     * Hàm tạo các dot dưới slide
     *
     * @created_by nxduong on 25/1/2021
     */

    @SuppressLint("UseCompatLoadingForDrawables")
    private void createDots() {
        mDotCount = mIntroAdapter.getCount();
        ivDots = new ImageView[mDotCount];
        for (int i = 0; i < mDotCount; i++) {
            ivDots[i] = new ImageView(this);
            if (i == 0) {
                ivDots[i].setBackground(getResources().getDrawable(R.drawable.dot_slide_selected));
            } else {
                ivDots[i].setBackground(getResources().getDrawable(R.drawable.dot_slide_unselect));
            }

            LinearLayout.LayoutParams params =
                    new LinearLayout.LayoutParams(20, 20);
            params.setMargins(8, 0, 8, 0);
            llDotsSplashIntro.addView(ivDots[i], params);
        }
    }

    //Khởi tạo / ánh xạ
    private void init() {
        llDotsSplashIntro = findViewById(R.id.llDotsSplashIntro);
        vpIntroSplashIntro = findViewById(R.id.vpIntroSplashIntro);

        btnRegisterSplashIntro = findViewById(R.id.btnRegisterSplashIntro);
        btnRegisterSplashIntro.setOnClickListener(this);
        tvLoginSplashIntro = findViewById(R.id.tvLoginSplashIntro);
        tvLoginSplashIntro.setOnClickListener(this);

        mSplashIntroPresenter = new SplashIntroPresenter(this, this);
        mSplashIntroPresenter.getSlideListIntro();

        new CopyMoneyDatabase(this); // Copy database vào trong máy android
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRegisterSplashIntro:
                startActivity(new Intent(this, RegisterActivity.class));
                break;

            case R.id.tvLoginSplashIntro:
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }
    }

    @Override
    public void resultSlideListIntros(ArrayList<Intro> listIntros) {
        mIntroAdapter = new ViewPagerIntroAdapter(this, listIntros);
        vpIntroSplashIntro.setAdapter(mIntroAdapter);
    }
}