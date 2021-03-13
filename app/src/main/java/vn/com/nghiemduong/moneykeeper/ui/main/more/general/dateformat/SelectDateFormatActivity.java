package vn.com.nghiemduong.moneykeeper.ui.main.more.general.dateformat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import vn.com.nghiemduong.moneykeeper.R;
import vn.com.nghiemduong.moneykeeper.ui.base.BaseActivity;
import vn.com.nghiemduong.moneykeeper.ui.main.MainActivity;
import vn.com.nghiemduong.moneykeeper.utils.AppConstants;
import vn.com.nghiemduong.moneykeeper.utils.AppUtils;

/**
 * - Màn hình chọn định dạng thời gian (date format)
 * - @created_by nxduong on 13/3/2021
 **/

public class SelectDateFormatActivity extends BaseActivity implements View.OnClickListener {

    private TextView tvDateFormatVN, tvDateFormatUS, tvDateFormatISO8601;
    private ImageView ivDateFormatVN, ivDateFormatUS, ivDateFormatISO8601;
    private Toolbar tbSelectDateFormat;

    private String dateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_date_format);

        init();

        dateFormat = AppUtils.getDateFormat(this);
        if (dateFormat.equals(AppConstants.FORMAT_TIME_VN)) {
            ivDateFormatVN.setVisibility(View.VISIBLE);
        } else if (dateFormat.equals(AppConstants.FORMAT_TIME_US)) {
            ivDateFormatUS.setVisibility(View.VISIBLE);
        } else {
            ivDateFormatISO8601.setVisibility(View.VISIBLE);
        }

        tbSelectDateFormat.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void init() {
        ivDateFormatVN = findViewById(R.id.ivDateFormatVN);
        tvDateFormatVN = findViewById(R.id.tvDateFormatVN);
        tvDateFormatVN.setText(AppConstants.FORMAT_TIME_VN);

        ivDateFormatUS = findViewById(R.id.ivDateFormatUS);
        tvDateFormatUS = findViewById(R.id.tvDateFormatUS);
        tvDateFormatUS.setText(AppConstants.FORMAT_TIME_US);

        ivDateFormatISO8601 = findViewById(R.id.ivDateFormatISO8601);
        tvDateFormatISO8601 = findViewById(R.id.tvDateFormatISO8601);
        tvDateFormatISO8601.setText(AppConstants.FORMAT_TIME_ISO_8601);

        RelativeLayout rlDateFormatVN = findViewById(R.id.rlDateFormatVN);
        rlDateFormatVN.setOnClickListener(this);

        RelativeLayout rlDateFormatUS = findViewById(R.id.rlDateFormatUS);
        rlDateFormatUS.setOnClickListener(this);

        RelativeLayout rlDateFormatISO8601 = findViewById(R.id.rlDateFormatISO8601);
        rlDateFormatISO8601.setOnClickListener(this);

        tbSelectDateFormat = findViewById(R.id.tbSelectDateFormat);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlDateFormatVN:
                try {
                    saveFormatDate(AppConstants.FORMAT_TIME_VN);
                } catch (Exception e) {
                    AppUtils.handlerException(e);
                }

                break;

            case R.id.rlDateFormatUS:
                try {
                    saveFormatDate(AppConstants.FORMAT_TIME_US);
                } catch (Exception e) {
                    AppUtils.handlerException(e);
                }
                break;

            case R.id.rlDateFormatISO8601:
                try {
                    saveFormatDate(AppConstants.FORMAT_TIME_ISO_8601);
                } catch (Exception e) {
                    AppUtils.handlerException(e);
                }
                break;
        }
    }

    public void saveFormatDate(String dateFormatSave) {
        SharedPreferences mSharedPreferences = this.getSharedPreferences(
                "MY_SHARED_PREFERENCES", MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString("DATE_FORMAT", dateFormatSave);
        editor.apply();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}