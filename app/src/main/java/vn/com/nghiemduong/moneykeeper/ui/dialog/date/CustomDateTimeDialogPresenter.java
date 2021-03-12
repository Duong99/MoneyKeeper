package vn.com.nghiemduong.moneykeeper.ui.dialog.date;

import java.util.Date;

/**
 * - @created_by nxduong on 30/1/2021
 **/
public class CustomDateTimeDialogPresenter implements CustomDateTimeDialogMvpPresenter {
    private CustomDateTimeDialogMvpView mCustomDateDialogMvpView;

    public CustomDateTimeDialogPresenter(CustomDateTimeDialogMvpView customDateDialogMvpView) {
        this.mCustomDateDialogMvpView = customDateDialogMvpView;
    }

    @Override
    public void doCustomDateTime(String mDate, String mTime) {
        int hour, minute;
        String[] timeArr = mTime.split(":");
        hour = Integer.parseInt(timeArr[0]);
        minute = Integer.parseInt(timeArr[1]);

        String[] dateArr = mDate.split("/");
        Date date = new Date();
//        date.setDate(Integer.parseInt(dateArr[0]));
//        date.setMonth(Integer.parseInt(dateArr[1]));
//        date.setYear(Integer.parseInt(dateArr[2]));

        mCustomDateDialogMvpView.onFinishCustomDateTime(date.getTime(), hour, minute);
    }

    /**
     * Hàm format mặc định này tháng MM/dd/yyy
     *
     * @created_by nxduong on 12/3/2021
     */
    @Override
    public void doFormatDayChange(int year, int month, int dayOfMonth) {
        String date;

        if ((month + 1) < 10) {
            date = "0" + (month + 1);
        } else {
            date = "" + (month + 1);
        }

        if (dayOfMonth < 10) {
            date += "/0" + dayOfMonth;
        } else {
            date += "/" + String.valueOf(dayOfMonth);
        }
        date += "/" + year;

        mCustomDateDialogMvpView.onFinishFormatDateChanged(date);
    }

    @Override
    public void doFormatTimeChange(int hourOfDay, int minute) {
        String time;
        if (hourOfDay < 10) {
            time = "0" + hourOfDay;
        } else {
            time = String.valueOf(hourOfDay);
        }

        if (minute < 10) {
            time += ":0" + minute;
        } else {
            time += ":" + minute;
        }

        mCustomDateDialogMvpView.onFinishFormatTimeChanged(time);
    }
}
