package vn.com.nghiemduong.moneykeeper.ui.main.accountoverview.account;

import java.util.ArrayList;

import vn.com.nghiemduong.moneykeeper.data.model.db.Account;

/**
 * - @created_by nxduong on 27/1/2021
 **/
public interface AccountFragmentMvpPresenter {
    void doSumOfMoneyOfAllAccount(ArrayList<Account> listAccount);
}
