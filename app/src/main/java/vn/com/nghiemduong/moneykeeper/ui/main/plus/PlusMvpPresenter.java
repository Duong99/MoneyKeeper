package vn.com.nghiemduong.moneykeeper.ui.main.plus;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import vn.com.nghiemduong.moneykeeper.data.model.db.Account;
import vn.com.nghiemduong.moneykeeper.data.model.db.Category;
import vn.com.nghiemduong.moneykeeper.data.model.db.Record;

/**
 * - @created_by nxduong on 25/1/2021
 **/
public interface PlusMvpPresenter {
    void addCategories();

    void initView(int position);

    void doGetBundleRecord(Fragment fg);

    void doGetChooseCategory(Intent data);

    void doGetChooseAccount(Intent data, int request_code);

    void doGetAccountFirstFromDB(Context context);

    void doGetDebtor(Intent data);

    void setScreenFunctionWithCategory(Category category);

    void saveRecord(Record mRecord, int amount, Category mCategory, String debtor, String explain, String date,
                    String time, Account mAccount, Account mToAccount, String dateDuration,
                    int report, byte[] image, int recordConstant);
}
