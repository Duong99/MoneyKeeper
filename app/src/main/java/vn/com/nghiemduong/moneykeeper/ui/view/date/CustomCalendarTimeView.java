package vn.com.nghiemduong.moneykeeper.ui.view.date;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;

import vn.com.nghiemduong.moneykeeper.R;

/**
 * -  Lớp kế thừa từ dialog custom lại CalendarView, TimePicker;
 * <p>
 * - @created_by nxduong on 29/1/2021
 **/
public class CustomCalendarTimeView extends Dialog implements View.OnClickListener {
    public final static int KEY_CALENDAR = 873;
    public final static int KEY_WATCH = 872;

    private CalendarView calendarView;
    private TimePicker timePicker;
    private int mKey;
    private Button btnCurrentDate, btnCloseDate, btnSaveDate;
    private TextView tvTime, tvDate;
    private ImageView ivTime;

    public CustomCalendarTimeView(@NonNull Context context, int key) {
        super(context);
        setContentView(R.layout.custom_calendar_time_view);
        this.mKey = key;

        init();
    }

    private void init() {
        calendarView = findViewById(R.id.calendarView);
        timePicker = findViewById(R.id.timePicker);

        btnCurrentDate = findViewById(R.id.btnCurrentDate);
        btnCurrentDate.setOnClickListener(this);

        btnCloseDate = findViewById(R.id.btnCloseDate);
        btnCloseDate.setOnClickListener(this);

        btnSaveDate = findViewById(R.id.btnSaveDate);
        btnSaveDate.setOnClickListener(this);

        tvTime = findViewById(R.id.tvTime);
        tvTime.setOnClickListener(this);

        ivTime = findViewById(R.id.ivTime);
        ivTime.setOnClickListener(this);

        tvDate = findViewById(R.id.tvDate);
        tvDate.setOnClickListener(this);

        if (mKey == KEY_CALENDAR) {
            timePicker.setVisibility(View.GONE);
            btnCurrentDate.setText(R.string.today);
        }

        if (mKey == KEY_WATCH) {
            calendarView.setVisibility(View.GONE);
            btnCurrentDate.setText(R.string.current_time);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCurrentDate:

                break;

            case R.id.btnCloseDate:
                dismiss();
                break;

            case R.id.btnSaveDate:

                break;

            case R.id.tvDate:
                break;

            case R.id.tvTime:

                break;

            case R.id.ivTime:
                break;
        }
    }
}
