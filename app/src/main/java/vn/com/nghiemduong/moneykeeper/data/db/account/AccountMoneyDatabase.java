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
    private final static String NAME_TABLE_ACCOUNT = "tb_Account";
    public final static String ACCOUNT_ID = "accountId";
    private final static String ACCOUNT_NAME = "accountName";
    private final static String ACCOUNT_MONEY_CURRENT = "moneyCurrent";
    private final static String ACCOUNT_TYPE_PATH = "accountTypePath";
    private final static String ACCOUNT_TYPE_NAME = "accountTypeName";
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

    /**
     * Hàm lấy danh sách tài khoản trong database
     *
     * @return listAccount
     * @created_by nxduong on 29/1/2021
     */
    @Override
    public void getAllAccount() {
        db = this.getReadableDatabase();
        ArrayList<Account> listAccount = new ArrayList<>();
        String query = "SELECT * FROM " + NAME_TABLE_ACCOUNT;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Account account = new Account(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getInt(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getInt(7));

                listAccount.add(account);
            } while (cursor.moveToNext());
        }
        db.close();
        mAccountMoneyDatabaseMvpView.getAllAccountResult(listAccount);
    }

    /**
     * Hàm thêm tài khoản vào trong database
     *
     * @param account
     * @created_by nxduong on 29/1/2021
     */

    @Override
    public void insertAccount(Account account) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ACCOUNT_NAME, account.getAccountName());
        values.put(ACCOUNT_MONEY_CURRENT, account.getMoneyCurrent());
        values.put(ACCOUNT_TYPE_PATH, account.getAccountTypePath());
        values.put(ACCOUNT_TYPE_NAME, account.getAccountTypeName());
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

    /**
     * Hàm cập nhật tài khoản vào trong database
     *
     * @param account
     * @created_by nxduong on 29/1/2021
     */
    @Override
    public void updateAccount(Account account) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ACCOUNT_NAME, account.getAccountName());
        values.put(ACCOUNT_MONEY_CURRENT, account.getMoneyCurrent());
        values.put(ACCOUNT_TYPE_PATH, account.getAccountTypePath());
        values.put(ACCOUNT_TYPE_NAME, account.getAccountTypeName());
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

    /**
     * Hàm xóa tài khoản trong database
     *
     * @param accountId
     * @created_by nxduong on 29/1/2021
     */

    @Override
    public void deleteAccount(int accountId) {
        db = this.getWritableDatabase();
        long delete = db.delete(NAME_TABLE_ACCOUNT, ACCOUNT_ID + " = ?",
                new String[]{String.valueOf(accountId)});

        if (delete != DBUtils.check) {
            mAccountMoneyDatabaseMvpView.deleteAccountSuccess();
        } else {
            mAccountMoneyDatabaseMvpView.deleteAccountFail();
        }
    }
}
