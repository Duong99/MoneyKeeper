package vn.com.nghiemduong.moneykeeper.data.db.MoneyCollect;

import vn.com.nghiemduong.moneykeeper.data.model.MoneyCollect;

/**
 * - @created_by nxduong on 2/2/2021
 **/
public interface MoneyCollectDatabaseMvpPresenter {
    void getAllMoneyCollect();

    void insertMoneyCollect(MoneyCollect moneyCollect);

    void updateMoneyCollect(MoneyCollect moneyCollect);

    void deleteMoneyCollect(int moneyCollectId);
}
