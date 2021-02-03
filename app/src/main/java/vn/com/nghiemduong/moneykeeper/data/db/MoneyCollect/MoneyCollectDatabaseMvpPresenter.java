package vn.com.nghiemduong.moneykeeper.data.db.MoneyCollect;

import java.util.ArrayList;

import vn.com.nghiemduong.moneykeeper.data.model.MoneyCollect;

/**
 * - @created_by nxduong on 2/2/2021
 **/
public interface MoneyCollectDatabaseMvpPresenter {
    ArrayList<MoneyCollect> getAllMoneyCollect();

    long insertMoneyCollect(MoneyCollect moneyCollect);

    long updateMoneyCollect(MoneyCollect moneyCollect);

    long deleteMoneyCollect(int moneyCollectId);

    long updateMoneyOfAccountWhenCollect(int accountId, int numberMoney);
}
