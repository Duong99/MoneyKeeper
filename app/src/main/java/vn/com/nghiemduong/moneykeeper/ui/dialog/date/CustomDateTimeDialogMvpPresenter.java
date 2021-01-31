package vn.com.nghiemduong.moneykeeper.ui.dialog.date;

/**
 * - @created_by nxduong on 30/1/2021
 **/
public interface CustomDateTimeDialogMvpPresenter {
    void doCustomDateTime(String mDate, String mTime);

    void doFormatDayChange(int year, int month, int dayOfMonth);

    void doFormatTimeChange(int hourOfDay, int minute);
}
