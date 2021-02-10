package vn.com.nghiemduong.moneykeeper.ui.main.plus.collect;

import android.content.Context;
import android.content.Intent;

import vn.com.nghiemduong.moneykeeper.data.model.Account;
import vn.com.nghiemduong.moneykeeper.data.model.Category;
import vn.com.nghiemduong.moneykeeper.data.model.MoneyCollect;
import vn.com.nghiemduong.moneykeeper.data.model.SubCategory;

/**
 * - @created_by nxduong on 1/2/2021
 **/
public interface CollectMoneyFragmentMvpView {
    void resultGetMoneyCollectFromBundle(MoneyCollect moneyCollect, Account account,
                                         Category category, SubCategory subCategory);

    void resultOnActivityResultChooseCategoryCollect(Category category, SubCategory subCategory);

    void resultOnFinishChooseAccountCollect(Account account);

    void resultGetAccountFirstFromDB(Account account);
}
