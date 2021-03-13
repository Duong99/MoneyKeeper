package vn.com.nghiemduong.moneykeeper.ui.dialog.date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;

import java.util.Date;

import vn.com.nghiemduong.moneykeeper.R;
import vn.com.nghiemduong.moneykeeper.utils.AppUtils;

/**
 * -  Lớp kế thừa dialog cho phép chọn lịch và thời gian
 * <p>
 * - @created_by nxduong on 29/1/2021
 **/
public class CustomDateTimeDialog extends Dialog implements View.OnClickListener,
        CustomDateTimeDialogMvpView {
    public final static int KEY_CALENDAR = 873;
    public final static int KEY_JUST_CALENDAR = 874;
    public final static int KEY_WATCH = 872;


    private CalendarView calendarView;
    private TimePicker timePicker;
    private int mKey;
    private TextView btnCurrentDate;
    private TextView tvTime, tvDate;
    private ImageView ivSwap;
    private String mDate, mTime;
    private CustomDateTimeDialogMvpPresenter mCustomDateTimeDialogMvpPresenter;
    private Context mContext;
    private RelativeLayout rlCenterTimePicker;
    private IOnClickSaveDateTime mOnClickSaveDateTime;

    public CustomDateTimeDialog(@NonNull Context context, int key, String date, String time,
                                IOnClickSaveDateTime onClickSaveDateTime) {
        super(context);
        setContentView(R.layout.dialog_calendar_time_view);
        this.mKey = key;
        this.mDate = date;
        this.mTime = time;
        this.mContext = context;
        this.mOnClickSaveDateTime = onClickSaveDateTime;
        init();

        onDateTimeChangedListener();
    }

    private void onDateTimeChangedListener() {
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(@NonNull CalendarView view,
                                            int year, int month, int dayOfMonth) {
                mCustomDateTimeDialogMvpPresenter.doFormatDayChange(year, month, dayOfMonth);
            }
        });

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                mCustomDateTimeDialogMvpPresenter.doFormatTimeChange(hourOfDay, minute);
            }
        });
    }

    // Khởi tạo / ánh xạ
    private void init() {
        calendarView = findViewById(R.id.calendarView);
        rlCenterTimePicker = findViewById(R.id.rlCenterTimePicker);

        timePicker = findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true);

        btnCurrentDate = findViewById(R.id.btnCurrentDate);
        btnCurrentDate.setOnClickListener(this);

        TextView btnCloseDate = findViewById(R.id.btnCloseDate);
        btnCloseDate.setOnClickListener(this);

        TextView btnSaveDate = findViewById(R.id.btnSaveDate);
        btnSaveDate.setOnClickListener(this);

        tvTime = findViewById(R.id.tvTime);
        tvTime.setOnClickListener(this);

        ivSwap = findViewById(R.id.ivSwap);
        ivSwap.setOnClickListener(this);
        tvTime.setText(mTime);

        tvDate = findViewById(R.id.tvDate);
        tvDate.setOnClickListener(this);
        tvDate.setText(mDate);

        mCustomDateTimeDialogMvpPresenter = new CustomDateTimeDialogPresenter(this);
        mCustomDateTimeDialogMvpPresenter.doCustomDateTime(mDate, mTime);

        setViewDateTime();
    }

    /**
     * -  Hàm dùng để set các view khi người dùng chọn lịch hay giờ
     * <p>
     * - @created_by nxduong on 30/1/2021
     **/
    private void setViewDateTime() {
        if (mKey == KEY_CALENDAR) {
            timePicker.setVisibility(View.GONE);
            rlCenterTimePicker.setVisibility(View.GONE);
            calendarView.setVisibility(View.VISIBLE);
            btnCurrentDate.setText(R.string.today);
            tvTime.setTextColor(mContext.getResources().getColor(R.color.gray_image_bottom_sheet));
            ivSwap.setColorFilter(mContext.getResources().getColor(R.color.gray_image_bottom_sheet));

            tvDate.setTextColor(mContext.getResources().getColor(R.color.white));
        }

        if (mKey == KEY_WATCH) {
            calendarView.setVisibility(View.GONE);
            timePicker.setVisibility(View.VISIBLE);
            rlCenterTimePicker.setVisibility(View.VISIBLE);
            btnCurrentDate.setText(R.string.current_time);
            tvTime.setTextColor(mContext.getResources().getColor(R.color.white));
            ivSwap.setColorFilter(mContext.getResources().getColor(R.color.white));
            tvDate.setTextColor(mContext.getResources().getColor(R.color.gray_image_bottom_sheet));
        }

        if (mKey == KEY_JUST_CALENDAR) {
            timePicker.setVisibility(View.GONE);
            rlCenterTimePicker.setVisibility(View.GONE);
            calendarView.setVisibility(View.VISIBLE);
            btnCurrentDate.setText(R.string.today);
            tvTime.setVisibility(View.GONE);
            ivSwap.setVisibility(View.GONE);
            tvDate.setTextColor(mContext.getResources().getColor(R.color.white));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCurrentDate: // Chọn ngày hiện tại
                Date date = new Date();
                if (mKey == KEY_WATCH) {
                    timePicker.setCurrentHour(date.getHours());
                    timePicker.setCurrentMinute(date.getMinutes());
                }

                if (mKey == KEY_CALENDAR || mKey == KEY_JUST_CALENDAR) {
                    calendarView.setDate(date.getTime());
                    mDate = AppUtils.getDateCurrent((Activity) mContext);
                    tvDate.setText(AppUtils.formatDate(mDate, (Activity) mContext));
                }

                break;

            case R.id.btnCloseDate: // Đóng
                dismiss();
                break;

            case R.id.btnSaveDate: // Chọn lưu ngày, thời gian được chọn
                if (mKey == KEY_JUST_CALENDAR) {
                    mOnClickSaveDateTime.saveDateDuration(mDate);
                } else {
                    mOnClickSaveDateTime.saveDateTime(mDate, mTime);
                }

                dismiss();
                break;

            case R.id.tvDate:
                if (mKey != KEY_CALENDAR) {
                    mKey = KEY_CALENDAR;
                    setViewDateTime();
                }
                break;

            case R.id.tvTime:
                if (mKey != KEY_WATCH) {
                    mKey = KEY_WATCH;
                    setViewDateTime();
                }
                break;
            case R.id.ivSwap:
                RotateAnimation animation;

                if (mKey != KEY_CALENDAR) {
                    mKey = KEY_CALENDAR;
                    animation = new RotateAnimation(0, 360,
                            RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                            RotateAnimation.RELATIVE_TO_SELF, 0.5f);
                } else {
                    mKey = KEY_WATCH;
                    animation = new RotateAnimation(360, 0,
                            RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                            RotateAnimation.RELATIVE_TO_SELF, 0.5f);
                }

                animation.setDuration(500);
                animation.setFillAfter(true);
                ivSwap.startAnimation(animation);
                setViewDateTime();
                break;
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onFinishCustomDateTime(long date, int hour, int minute) {
        calendarView.setDate(date);
        timePicker.setCurrentHour(hour);
        timePicker.setCurrentMinute(minute);
    }

    @Override
    public void onFinishFormatDateChanged(String date) {
        mDate = date;
        tvDate.setText(AppUtils.formatDate(mDate, (Activity) mContext));
    }

    @Override
    public void onFinishFormatTimeChanged(String time) {
        mTime = time;
        tvTime.setText(mTime);
    }

    public interface IOnClickSaveDateTime {
        void saveDateTime(String date, String time);

        void saveDateDuration(String dateDuration);
    }
}
