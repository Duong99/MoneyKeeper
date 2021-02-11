package vn.com.nghiemduong.moneykeeper.ui.main.plus.transfer;

import android.content.Context;
import android.content.Intent;

/**
 * -
 * - @created_by nxduong on 11/2/2021
 **/
public interface TransferFragmentMvpPresenter {

    void doGetFromAccountFirstFromDB();

    void doGetOnActivityFromAccount(Intent data);

    void doGetOnActivityToAccount(Intent data);
}
