package vn.com.nghiemduong.moneykeeper.data.db.account;

import java.util.ArrayList;

import vn.com.nghiemduong.moneykeeper.data.model.Account;

/**
 * - @created_by nxduong on 26/1/2021
 **/
public interface AccountMoneyDatabaseMvpPresenter {
    ArrayList<Account> getAllAccount();

    Account getAccount(int accountId);

    long insertAccount(Account account);

    long updateAccount(Account account);

    long deleteAccount(int accountId);

    long subtractMoneyOfAccount(int accountId, int numberMoney);

    long plusMoneyOfAccount(int accountId, int numberMoney);
}
