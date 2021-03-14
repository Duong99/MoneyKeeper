package vn.com.nghiemduong.moneykeeper.ui.main.accountoverview.add;

import android.app.Activity;
import android.content.Context;

import java.util.Objects;

import vn.com.nghiemduong.moneykeeper.data.model.db.Account;

/**
 * - @created_by nxduong on 26/1/2021
 **/
public class AddAccountActivityPresenter implements AddAccountActivityMvpPresenter {
    private AddAccountActivityMvpView mAddAccountActivityMvpView;

    public AddAccountActivityPresenter(AddAccountActivityMvpView addAccountActivityMvpView) {
        this.mAddAccountActivityMvpView = addAccountActivityMvpView;
    }

    /**
     * Hàm lấy đối tượng tài khoản
     *
     * @created_by nxduong on 14/3/2021
     */
    @Override
    public void getAccountBundle(Activity activity) {
        Account account = (Account) Objects.requireNonNull(activity.getIntent().getBundleExtra("BUNDLE"))
                .getSerializable("BUNDLE_ACCOUNT");
        mAddAccountActivityMvpView.resultGetAccountBundle(account);
    }
}
