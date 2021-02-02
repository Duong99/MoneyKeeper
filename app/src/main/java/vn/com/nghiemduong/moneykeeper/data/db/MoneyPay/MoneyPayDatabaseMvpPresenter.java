package vn.com.nghiemduong.moneykeeper.data.db.MoneyPay;

import vn.com.nghiemduong.moneykeeper.data.model.MoneyPay;

/**
 * - @created_by nxduong on 2/2/2021
 **/
public interface MoneyPayDatabaseMvpPresenter {
    void getAllMoneyPay();

    void insertMoneyPay(MoneyPay moneyPay);

    void updateMoneyPay(MoneyPay moneyPay);

    void deleteMoneyPay(int moneyPayId);
}
