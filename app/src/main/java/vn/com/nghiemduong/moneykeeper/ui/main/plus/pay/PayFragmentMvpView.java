package vn.com.nghiemduong.moneykeeper.ui.main.plus.pay;

import vn.com.nghiemduong.moneykeeper.data.model.db.Account;
import vn.com.nghiemduong.moneykeeper.data.model.db.Category;
import vn.com.nghiemduong.moneykeeper.data.model.db.MoneyPay;

/**
 * - @created_by nxduong on 26/1/2021
 **/
public interface PayFragmentMvpView {

    void resultChooseCategory(Category category);

    void resultChooseAccount(Account account);

    void resultGetAccountFirstFromDB(Account account);
}
