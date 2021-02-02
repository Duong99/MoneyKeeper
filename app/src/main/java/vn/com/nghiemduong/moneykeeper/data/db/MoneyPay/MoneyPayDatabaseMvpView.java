package vn.com.nghiemduong.moneykeeper.data.db.MoneyPay;

import java.util.ArrayList;

import vn.com.nghiemduong.moneykeeper.data.model.MoneyPay;

/**
 * - @created_by nxduong on 2/2/2021
 **/
public interface MoneyPayDatabaseMvpView {
    void getAllMoneyPayResult(ArrayList<MoneyPay> listMoneyPay);

    void insertMoneyPaySuccess();

    void insertMoneyPayFail();

    void updateMoneyPaySuccess();

    void updateMoneyPayFail();

    void deleteMoneyPaySuccess();

    void deleteMoneyPayFail();
}
