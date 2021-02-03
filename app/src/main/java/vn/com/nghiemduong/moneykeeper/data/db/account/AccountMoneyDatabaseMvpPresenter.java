package vn.com.nghiemduong.moneykeeper.data.db.account;

import java.util.ArrayList;

import vn.com.nghiemduong.moneykeeper.data.model.Account;

/**
 * - @created_by nxduong on 26/1/2021
 **/
public interface AccountMoneyDatabaseMvpPresenter {
    ArrayList<Account> getAllAccount();

    long insertAccount(Account account);

    long updateAccount(Account account);

    long deleteAccount(int accountId);
}
