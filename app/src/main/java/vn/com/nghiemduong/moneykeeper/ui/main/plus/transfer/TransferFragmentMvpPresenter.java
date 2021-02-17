package vn.com.nghiemduong.moneykeeper.ui.main.plus.transfer;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

/**
 * -
 * - @created_by nxduong on 11/2/2021
 **/
public interface TransferFragmentMvpPresenter {

    void doGetTransferFromBundle(Fragment fm, Context context);

    void doGetFromAccountFirstFromDB();

    void doGetOnActivityFromAccount(Intent data);

    void doGetOnActivityToAccount(Intent data);
}
