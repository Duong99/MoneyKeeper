package vn.com.nghiemduong.moneykeeper.ui.main.plus.pay;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

/**
 * - @created_by nxduong on 26/1/2021
 **/
public interface PayFragmentMvpPresenter {

    void doGetChooseCategory(Intent data);

    void doGetChooseAccount(Intent data);

    void doGetAccountFirstFromDB(Context context);
}
