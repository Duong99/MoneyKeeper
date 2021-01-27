package vn.com.nghiemduong.moneykeeper.data.db.account;

import vn.com.nghiemduong.moneykeeper.data.model.Account;

/**

 * - @created_by nxduong on 26/1/2021
 **/
public interface AccountMoneyDatabaseMvpPresenter {
    void getAllAccount();

    void insertAccount(Account account);

    void updateAccount(Account account);

    void deleteAccount(int accountId);
}
