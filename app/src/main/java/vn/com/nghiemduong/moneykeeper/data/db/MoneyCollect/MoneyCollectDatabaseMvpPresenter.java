package vn.com.nghiemduong.moneykeeper.data.db.MoneyCollect;

import java.util.ArrayList;

import vn.com.nghiemduong.moneykeeper.data.model.MoneyCollect;

/**
 * - @created_by nxduong on 2/2/2021
 **/
public interface MoneyCollectDatabaseMvpPresenter {
    ArrayList<MoneyCollect> getAllMoneyCollect();

    long insertMoneyCollect(MoneyCollect moneyCollect);

    long updateMoneyCollect(MoneyCollect moneyCollect, int numberMoneyPrevious);

    long deleteMoneyCollect(MoneyCollect moneyCollect);

    long updateMoneyOfAccountWhenInsertCollect(int accountId, int numberMoney);

    long updateMoneyOfAccountWhenDeleteCollect(int accountId, int numberMoney);

    long updateMoneyOfAccountWhenUpdateCollect(int accountId, int numberMoneyCurrent,
                                               int numberMoneyPrevious);
}
