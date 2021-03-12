package vn.com.nghiemduong.moneykeeper.ui.main.plus;

import java.util.ArrayList;

import vn.com.nghiemduong.moneykeeper.data.model.HeaderCategory;
import vn.com.nghiemduong.moneykeeper.data.model.db.Account;
import vn.com.nghiemduong.moneykeeper.data.model.db.Category;
import vn.com.nghiemduong.moneykeeper.data.model.db.Record;

/**
 * - @created_by nxduong on 25/1/2021
 **/
public interface PlusMvpView {

    void resultGetBundleRecord(Record record);

    void initViewPay();

    void initViewDebtPay();

    void initViewCollect();

    void initDebtCollect();

    void initViewLoan();

    void initViewBorrow();

    void initViewTransfer();

    void resultListCategories(ArrayList<HeaderCategory> listCategory);

    void resultChooseCategory(Category category);

    void resultChooseDebtor(Record debtor);

    void resultChooseAccount(Account account, int request_code);

    void resultGetAccountFirstFromDB(Account account);

    void showCustomToastChooseCategoryWarring(String message);

    void showCustomToastChooseAccountWarring(String message);

    void showCustomToastChooseFromAccountWarring(String message);

    void showCustomToastChooseToAccountWarring(String message);

    void showCustomToastChooseDebtorWarring(String message);

    void showCustomToastChooseDateDurationWarring(String message);

    void saveRecordSuccess(String message);

    void saveRecordFail(String message);
}
