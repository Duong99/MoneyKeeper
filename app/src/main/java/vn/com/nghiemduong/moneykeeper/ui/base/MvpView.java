package vn.com.nghiemduong.moneykeeper.ui.base;

/**
 * - @created_by nxduong on 21/1/2021
 **/
public interface MvpView {

    void showToast(String message);

    void showCustomToast(String message, int type);

    void onBackPressed();
}
