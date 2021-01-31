package vn.com.nghiemduong.moneykeeper.ui.main.accountoverview.add;

/**
 * - @created_by nxduong on 26/1/2021
 **/
public class AddAccountActivityPresenter implements AddAccountActivityMvpPresenter {
    private AddAccountActivityMvpView mAddAccountActivityMvpView;

    public AddAccountActivityPresenter(AddAccountActivityMvpView addAccountActivityMvpView) {
        this.mAddAccountActivityMvpView = addAccountActivityMvpView;
    }
}
