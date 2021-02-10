package vn.com.nghiemduong.moneykeeper.ui.main.plus.pay;

import vn.com.nghiemduong.moneykeeper.data.model.Account;
import vn.com.nghiemduong.moneykeeper.data.model.Category;
import vn.com.nghiemduong.moneykeeper.data.model.MoneyCollect;
import vn.com.nghiemduong.moneykeeper.data.model.MoneyPay;
import vn.com.nghiemduong.moneykeeper.data.model.SubCategory;

/**
 * - @created_by nxduong on 26/1/2021
 **/
public interface PayFragmentMvpView {
    void resultGetMoneyPayFromBundle(MoneyPay moneyPay, Account account,
                                     Category category, SubCategory subCategory);

    void resultOnActivityResultChooseCategoryPay(Category category, SubCategory subCategory);

    void resultOnFinishChooseAccountPay(Account account);

    void resultGetAccountFirstFromDB(Account account);
}
