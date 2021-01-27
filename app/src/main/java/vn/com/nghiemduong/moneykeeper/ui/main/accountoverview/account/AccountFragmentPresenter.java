package vn.com.nghiemduong.moneykeeper.ui.main.accountoverview.account;

/**
 * -
 * <p>
 * <p>
 * - @created_by nxduong on 2/1/2021
 **/
public class AccountFragmentPresenter implements AccountFragmentMvpPresenter{
    private AccountFragmentMvpView mAccountFragmentMvpView;

    public AccountFragmentPresenter(AccountFragmentMvpView accountFragmentMvpView) {
        this.mAccountFragmentMvpView = accountFragmentMvpView;
    }
}
