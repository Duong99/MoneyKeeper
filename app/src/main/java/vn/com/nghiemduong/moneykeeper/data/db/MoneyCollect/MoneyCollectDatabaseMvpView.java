package vn.com.nghiemduong.moneykeeper.data.db.MoneyCollect;

import java.util.ArrayList;

import vn.com.nghiemduong.moneykeeper.data.model.MoneyCollect;

/**
 * - @created_by nxduong on 2/2/2021
 **/
public interface MoneyCollectDatabaseMvpView {
    void getAllMoneyCollectResult(ArrayList<MoneyCollect> listMoneyCollect);

    void insertMoneyCollectSuccess();

    void insertMoneyCollectFail();

    void updateMoneyCollectSuccess();

    void updateMoneyCollectFail();

    void deleteMoneyCollectSuccess();

    void deleteMoneyCollectFail();
}
