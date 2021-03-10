package vn.com.nghiemduong.moneykeeper.ui.main.plus;

import java.util.ArrayList;

import vn.com.nghiemduong.moneykeeper.data.model.HeaderCategory;
import vn.com.nghiemduong.moneykeeper.data.model.db.Account;
import vn.com.nghiemduong.moneykeeper.data.model.db.Category;

/**
 * - @created_by nxduong on 25/1/2021
 **/
public interface PlusMvpView {

    void initViewPay();

    void initViewDebtPay();

    void initViewCollect();

    void initDebtCollect();

    void initViewLoan();

    void initViewBorrow();

    void initViewTransfer();

    void resultListCategories(ArrayList<HeaderCategory> listCategory);

    void resultChooseCategory(Category category);

    void resultChooseAccount(Account account, int request_code);

    void resultGetAccountFirstFromDB(Account account);
}
