package vn.com.nghiemduong.moneykeeper.ui.main.plus;

import android.content.Context;
import android.content.Intent;

import vn.com.nghiemduong.moneykeeper.data.model.db.Account;
import vn.com.nghiemduong.moneykeeper.data.model.db.Category;

/**
 * - @created_by nxduong on 25/1/2021
 **/
public interface PlusMvpPresenter {
    void addCategories();

    void initView(int position);

    void doGetChooseCategory(Intent data);

    void doGetChooseAccount(Intent data, int request_code);

    void doGetAccountFirstFromDB(Context context);

    void setScreenFunctionWithCategory(Category category);

    void saveRecord(int amount, Category mCategory, String debtor, String explain, String date,
                    String time, Account mAccount, Account mToAccount, String dateDuration,
                    int report, byte[] image, int recordConstant);
}
