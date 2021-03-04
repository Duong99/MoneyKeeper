package vn.com.nghiemduong.moneykeeper.data.db.moneyPay;

import java.util.ArrayList;

import vn.com.nghiemduong.moneykeeper.data.model.db.MoneyPay;

/**
 * - @created_by nxduong on 2/2/2021
 **/
public interface MoneyPayDatabaseMvpPresenter {
    ArrayList<MoneyPay> getAllMoneyPay();

    ArrayList<MoneyPay> searchMoneyPayByDate(String fromDate, String toDate);

    long insertMoneyPay(MoneyPay moneyPay);

    long updateMoneyPay(MoneyPay moneyPay, int numberMoneyPrevious);

    long deleteMoneyPay(MoneyPay moneyPay);

    long updateMoneyOfAccountWhenUpdatePay(int accountId, int numberMoneyCurrent,
                                           int numberMoneyPrevious);
}
