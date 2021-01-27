package vn.com.nghiemduong.moneykeeper.data.db.account;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import vn.com.nghiemduong.moneykeeper.data.model.Account;
import vn.com.nghiemduong.moneykeeper.utils.DBUtils;

/**
 * -
 * Lớp thực hiện thêm sửa xóa table Account trong database
 * <p>
 * - @created_by nxduong on 26/1/2021
 **/
public class AccountMoneyDatabase extends SQLiteOpenHelper implements AccountMoneyDatabaseMvpPresenter {
    private final static String NAME_TABLE_ACCOUNT = "Account";
    private final static String ACCOUNT_ID = "accountId";
    private final static String ACCOUNT_MONEY_CURRENT = "moneyCurrent";
    private final static String ACCOUNT_NAME = "accountName";
    private final static String ACCOUNT_TYPE = "accountType";
    private final static String ACCOUNT_IMAGE_TYPE = "imageAccountType";
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
        db = this.getReadableDatabase();
        ArrayList<Account> listAccount = new ArrayList<>();
        String query = "SELECT * FROM " + NAME_TABLE_ACCOUNT;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Account account = new Account(cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getString(2),
                        cursor.getInt(3),
                        cursor.getBlob(4),
                        cursor.getInt(5),
                        cursor.getString(6),
                        cursor.getInt(7));
                listAccount.add(account);
            } while (cursor.moveToNext());
        }
        db.close();
        mAccountMoneyDatabaseMvpView.getAllAccountResult(listAccount);
    }

    @Override
    public void insertAccount(Account account) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ACCOUNT_NAME, account.getAccountName());
        values.put(ACCOUNT_MONEY_CURRENT, account.getMoneyCurrent());
        values.put(ACCOUNT_TYPE, account.getAccountType());
        values.put(ACCOUNT_IMAGE_TYPE, account.getImageType());
        values.put(ACCOUNT_MONEY_TYPE, account.getMoneyType());
        values.put(ACCOUNT_EXPLAIN, account.getExplain());
        values.put(ACCOUNT_REPORT, account.getReport());
        long insert = db.insert(NAME_TABLE_ACCOUNT, null, values);

        db.close();

        if (insert != DBUtils.check) {
            mAccountMoneyDatabaseMvpView.insertAccountSuccess();
        } else {
            mAccountMoneyDatabaseMvpView.insertAccountFail();
        }
    }

    @Override
    public void updateAccount(Account account) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ACCOUNT_NAME, account.getAccountName());
        values.put(ACCOUNT_MONEY_CURRENT, account.getMoneyCurrent());
        values.put(ACCOUNT_TYPE, account.getAccountType());
        values.put(ACCOUNT_IMAGE_TYPE, account.getImageType());
        values.put(ACCOUNT_MONEY_TYPE, account.getMoneyType());
        values.put(ACCOUNT_EXPLAIN, account.getExplain());
        values.put(ACCOUNT_REPORT, account.getReport());
        long update = db.update(NAME_TABLE_ACCOUNT, values, ACCOUNT_ID + " = ? ",
                new String[]{String.valueOf(account.getAccountId())});

        db.close();

        if (update != DBUtils.check) {
            mAccountMoneyDatabaseMvpView.updateAccountSuccess();
        } else {
            mAccountMoneyDatabaseMvpView.updateAccountFail();
        }
    }

    @Override
    public void deleteAccount(int accountId) {
        db = this.getWritableDatabase();
        long delete = db.delete(NAME_TABLE_ACCOUNT, ACCOUNT_ID + " = ?",
                new String[]{String.valueOf(accountId)});

        if (delete != DBUtils.check){
            mAccountMoneyDatabaseMvpView.deleteAccountSuccess();
        }else {
            mAccountMoneyDatabaseMvpView.deleteAccountFail();
        }
    }
}
