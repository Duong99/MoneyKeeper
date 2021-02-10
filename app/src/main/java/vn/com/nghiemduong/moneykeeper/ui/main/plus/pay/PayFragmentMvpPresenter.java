package vn.com.nghiemduong.moneykeeper.ui.main.plus.pay;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

/**
 * - @created_by nxduong on 26/1/2021
 **/
public interface PayFragmentMvpPresenter {
    void doGetMoneyPayFromBundle(Fragment fm, Context context);

    void onActivityResultChooseCategoryPay(Intent data, Context context);

    void onActivityResultAccountPay(Intent data);

    void doGetAccountFirstFromDB(Context context);
}
