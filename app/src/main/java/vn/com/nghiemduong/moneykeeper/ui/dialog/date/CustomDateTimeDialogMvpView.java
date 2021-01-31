package vn.com.nghiemduong.moneykeeper.ui.dialog.date;

/**
 * - @created_by nxduong on 30/1/2021
 **/
public interface CustomDateTimeDialogMvpView {
    void onFinishCustomDateTime(long date, int hour, int minute);

    void onFinishFormatDateChanged(String date);

    void onFinishFormatTimeChanged(String time);
}
