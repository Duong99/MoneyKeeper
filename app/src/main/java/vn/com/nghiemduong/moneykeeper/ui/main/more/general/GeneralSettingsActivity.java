package vn.com.nghiemduong.moneykeeper.ui.main.more.general;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import vn.com.nghiemduong.moneykeeper.R;
import vn.com.nghiemduong.moneykeeper.ui.base.BaseActivity;
import vn.com.nghiemduong.moneykeeper.ui.main.more.general.dateformat.SelectDateFormatActivity;
import vn.com.nghiemduong.moneykeeper.utils.AppUtils;

/**
 * Màn hình cài đặt chung
 *
 * @created_by nxduong on 13/3/2021
 */
public class GeneralSettingsActivity extends BaseActivity implements View.OnClickListener {

    private Toolbar tbGeneralSettings;
    private TextView tvDateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_settings);

        init();

        tbGeneralSettings.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void init() {
        tbGeneralSettings = findViewById(R.id.tbGeneralSettings);

        RelativeLayout rlDateFormat = findViewById(R.id.rlDateFormat);
        rlDateFormat.setOnClickListener(this);
        tvDateFormat = findViewById(R.id.tvDateFormat);
        tvDateFormat.setText(AppUtils.getDateFormat(this));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlDateFormat: // Chọn (date format) định dạng thời gian
                Intent intentSelectDateFormat = new Intent(this, SelectDateFormatActivity.class);
                startActivity(intentSelectDateFormat);
                break;
        }
    }
}