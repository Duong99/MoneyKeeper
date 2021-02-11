package vn.com.nghiemduong.moneykeeper.ui.main.plus.transfer;

import android.content.Context;
import android.content.Intent;

import java.util.Objects;

import vn.com.nghiemduong.moneykeeper.data.db.account.AccountMoneyDatabase;
import vn.com.nghiemduong.moneykeeper.data.model.Account;

/**
 * -
 * - @created_by nxduong on 11/2/2021
 **/
public class TransferFragmentPresenter implements TransferFragmentMvpPresenter {
    private TransferFragmentMvpView mTransferFragmentMvpView;
    private Context mContext;

    public TransferFragmentPresenter(TransferFragmentMvpView transferFragmentMvpView, Context context) {
        this.mTransferFragmentMvpView = transferFragmentMvpView;
        this.mContext = context;
    }

    /**
     * Hàm lấy tài khoản đầu tiên trong database
     *
     * @created_by nxduong on 11/2/2021
     */
    @Override
    public void doGetFromAccountFirstFromDB() {
        mTransferFragmentMvpView.resultGetFromAccountFirstFromDB(
                new AccountMoneyDatabase(mContext).getAccountFirstly());
    }

    @Override
    public void doGetOnActivityFromAccount(Intent data) {
        Account fromAccount = (Account) Objects.requireNonNull(data.getBundleExtra("BUNDLE"))
                .getSerializable("BUNDLE_ACCOUNT");
        mTransferFragmentMvpView.resultGetOnActivityFromAccount(fromAccount);
    }

    @Override
    public void doGetOnActivityToAccount(Intent data) {
        Account toAccount = (Account) Objects.requireNonNull(data.getBundleExtra("BUNDLE"))
                .getSerializable("BUNDLE_ACCOUNT");
        mTransferFragmentMvpView.resultGetOnActivityToAccount(toAccount);
    }
}
