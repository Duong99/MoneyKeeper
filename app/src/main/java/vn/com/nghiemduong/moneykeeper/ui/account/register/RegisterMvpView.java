package vn.com.nghiemduong.moneykeeper.ui.account.register;

import vn.com.nghiemduong.moneykeeper.data.model.db.User;

/**
 * - @created_by nxduong on 22/1/2021
 **/
public interface RegisterMvpView {
    void registerAccountSuccess(String message);

    void registerAccountFail(String message);
}
