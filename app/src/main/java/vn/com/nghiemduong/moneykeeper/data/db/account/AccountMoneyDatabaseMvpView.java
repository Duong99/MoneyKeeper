package vn.com.nghiemduong.moneykeeper.data.db.account;

import java.util.ArrayList;

import vn.com.nghiemduong.moneykeeper.data.model.Account;

/**
 * - @created_by nxduong on 26/1/2021
 **/
public interface AccountMoneyDatabaseMvpView {
    void getAllAccountResult(ArrayList<Account> listAccount);

    void insertAccountSuccess();

    void insertAccountFail();

    void updateAccountSuccess();

    void updateAccountFail();

    void deleteAccountSuccess();

    void deleteAccountFail();
}
