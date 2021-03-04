package vn.com.nghiemduong.moneykeeper.ui.main.plus.transfer;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import java.util.Objects;

import vn.com.nghiemduong.moneykeeper.data.db.account.AccountMoneyDatabase;
import vn.com.nghiemduong.moneykeeper.data.model.db.Account;
import vn.com.nghiemduong.moneykeeper.data.model.db.Transfer;

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

    @Override
    public void doGetTransferFromBundle(Fragment fm, Context context) {
        if (fm.getArguments() != null) {
            Transfer transfer = (Transfer) fm.getArguments().getSerializable("BUNDLE_TRANSFER");
            if (transfer != null) {
                Account fromAccount, toAccount;

                AccountMoneyDatabase accountMoneyDatabase = new AccountMoneyDatabase(context);
                fromAccount = accountMoneyDatabase.getAccount(transfer.getFromAccountId());
                toAccount = accountMoneyDatabase.getAccount(transfer.getToAccountId());

                mTransferFragmentMvpView.resultGetTransferFromBundle(transfer, fromAccount, toAccount);
            }
        }
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
