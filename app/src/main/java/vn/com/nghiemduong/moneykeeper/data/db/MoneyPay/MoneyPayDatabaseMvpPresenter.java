package vn.com.nghiemduong.moneykeeper.data.db.MoneyPay;

import java.util.ArrayList;

import vn.com.nghiemduong.moneykeeper.data.model.MoneyPay;

/**
 * - @created_by nxduong on 2/2/2021
 **/
public interface MoneyPayDatabaseMvpPresenter {
    ArrayList<MoneyPay> getAllMoneyPay();

    long insertMoneyPay(MoneyPay moneyPay);

    long updateMoneyPay(MoneyPay moneyPay);

    long deleteMoneyPay(int moneyPayId);

    long updateMoneyOfAccountWhenPay(int accountId, int numberMoney);
}
