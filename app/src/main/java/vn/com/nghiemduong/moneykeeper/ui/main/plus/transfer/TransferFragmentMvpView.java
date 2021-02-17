package vn.com.nghiemduong.moneykeeper.ui.main.plus.transfer;

import android.content.Intent;

import vn.com.nghiemduong.moneykeeper.data.model.Account;
import vn.com.nghiemduong.moneykeeper.data.model.Transfer;

/**
 * -
 * - @created_by nxduong on 11/2/2021
 **/
public interface TransferFragmentMvpView {

    void resultGetTransferFromBundle(Transfer transfer, Account fromAccount, Account toAccount);

    void resultGetFromAccountFirstFromDB(Account fromAccount);

    void resultGetOnActivityFromAccount(Account fromAccount);

    void resultGetOnActivityToAccount(Account toAccount);
}
