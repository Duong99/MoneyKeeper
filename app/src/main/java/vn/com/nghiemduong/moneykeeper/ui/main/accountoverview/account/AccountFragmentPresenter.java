package vn.com.nghiemduong.moneykeeper.ui.main.accountoverview.account;

import java.util.ArrayList;

import vn.com.nghiemduong.moneykeeper.data.model.db.Account;

/**
 * - @created_by nxduong on 2/1/2021
 **/
public class AccountFragmentPresenter implements AccountFragmentMvpPresenter {
    private AccountFragmentMvpView mAccountFragmentMvpView;

    public AccountFragmentPresenter(AccountFragmentMvpView accountFragmentMvpView) {
        this.mAccountFragmentMvpView = accountFragmentMvpView;
    }

    /**
     * Hàm tính tổng tiền của tất cả tài khoản trong màn hình tài khoảng
     * - {@link AccountFragment}
     * - @created_by nxduong on 29/1/2021
     **/

    @Override
    public void doSumOfMoneyOfAllAccount(ArrayList<Account> listAccount) {
        int totalMoney = 0;
        for (int i = 0; i < listAccount.size(); i++) {
            totalMoney += listAccount.get(i).getCurrentAmount();
        }

        mAccountFragmentMvpView.onTotalMoneyOfAllAccount(totalMoney);
    }
}
