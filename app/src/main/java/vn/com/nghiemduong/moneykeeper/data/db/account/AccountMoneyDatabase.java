package vn.com.nghiemduong.moneykeeper.data.db.account;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import vn.com.nghiemduong.moneykeeper.data.model.Account;
import vn.com.nghiemduong.moneykeeper.utils.DBUtils;

/**
 * -
 * Lớp thực hiện thêm sửa xóa table Account trong database
 * <p>
 * - @created_by nxduong on 26/1/2021
 **/
public class AccountMoneyDatabase extends SQLiteOpenHelper implements AccountMoneyDatabaseMvpPresenter{
    private final static String NAME_TABLE_ACCOUNT = "Account";
    private final static String ACCOUNT_ID = "accountId";
    private final static String ACCOUNT_MONEY_CURRENT = "moneyCurrent";
    private final static String ACCOUNT_NAME = "accountName";
    private final static String ACCOUNT_TYPE = "accountType";
    private final static String ACCOUNT_IMAGE_TYPE = "imageType";
    private final static String ACCOUNT_MONEY_TYPE = "moneyType";
    private final static String ACCOUNT_EXPLAIN = "explain";
    private final static String ACCOUNT_REPORT = "report";

    private AccountMoneyDatabaseMvpView mAccountMoneyDatabaseMvpView;
    private SQLiteDatabase db;

    public AccountMoneyDatabase(@Nullable Context context,
                                AccountMoneyDatabaseMvpView accountMoneyDatabaseMvpView) {
        super(context, DBUtils.DB_NAME, null, DBUtils.DATABASE_VERSION);
        this.mAccountMoneyDatabaseMvpView = accountMoneyDatabaseMvpView;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void getAllAccount() {

    }

    @Override
    public void insertAccount(Account account) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ACCOUNT_NAME, account.getAccountName());
        values.put(ACCOUNT_MONEY_CURRENT, account.getMoneyCurrent());
        values.put(ACCOUNT_TYPE, account.getAccountType());
        values.put(ACCOUNT_IMAGE_TYPE, account.getImageType());
        values.put(ACCOUNT_MONEY_CURRENT, account.getMoneyType());
        values.put(ACCOUNT_EXPLAIN, account.getExplain());
        values.put(ACCOUNT_REPORT, account.getReport());
        long insert = db.insert(NAME_TABLE_ACCOUNT, null, values);
        if (insert != DBUtils.check){
            mAccountMoneyDatabaseMvpView.insertAccountSuccess();
        }else {
            mAccountMoneyDatabaseMvpView.insertAccountFail();
        }
    }

    @Override
    public void updateAccount(Account account) {

    }

    @Override
    public void deleteAccount(int accountId) {

    }
}
