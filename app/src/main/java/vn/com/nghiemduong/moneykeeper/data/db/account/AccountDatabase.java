package vn.com.nghiemduong.moneykeeper.data.db.account;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import vn.com.nghiemduong.moneykeeper.data.db.BaseSqLite;
import vn.com.nghiemduong.moneykeeper.data.model.db.Account;
import vn.com.nghiemduong.moneykeeper.utils.AppUtils;
import vn.com.nghiemduong.moneykeeper.utils.DBUtils;

/**
 * Lớp thực hiện thêm sửa xóa table Account trong database
 * - @created_by nxduong on 26/1/2021
 **/
public class AccountDatabase extends BaseSqLite implements AccountMoneyDatabaseMvpPresenter {
    public final static String NAME_TABLE_ACCOUNT = "tb_Account";
    public final static String ACCOUNT_ID = "accountId";
    private final static String NAME = "accountName";
    public final static String MONEY_CURRENT = "currentAmount";
    private final static String TYPE_PATH = "accountTypePath";
    private final static String TYPE_NAME = "accountTypeName";
    private final static String MONEY_TYPE = "moneyType";
    private final static String DESCRIPTION = "description";
    private final static String REPORT = "report";

    private SQLiteDatabase db;

    public AccountDatabase(@Nullable Context context) {
        super(context);
    }


    /**
     * Hàm lấy danh sách tài khoản trong database
     *
     * @return listAccount
     * @created_by nxduong on 29/1/2021
     */
    @Override
    public ArrayList<Account> getAllAccount() {
        db = this.getReadableDatabase();
        ArrayList<Account> listAccount = new ArrayList<>();
        String query = "SELECT * FROM " + NAME_TABLE_ACCOUNT;
        try {
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
        } catch (Exception e) {
            AppUtils.handlerException(e);
        }

        db.close();
        return listAccount;
    }

    /**
     * Lấy tài khoản đầu tiên
     *
     * @created_by nxduong on 10/2/2021
     */
    public Account getAccountFirstly() {
        db = this.getReadableDatabase();
        Account account = null;
        String query = "SELECT * FROM " + NAME_TABLE_ACCOUNT + " LIMIT 1";

        try {
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    account = new Account(cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getInt(2),
                            cursor.getString(3),
                            cursor.getString(4),
                            cursor.getString(5),
                            cursor.getString(6),
                            cursor.getInt(7));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            AppUtils.handlerException(e);
        }

        db.close();
        return account;
    }

    /**
     * Hàm lấy tài khoản trong database
     *
     * @return listAccount
     * @created_by nxduong on 10/2/2021
     */
    @Override
    public Account getAccount(int accountId) {
        db = this.getReadableDatabase();
        Account account = null;
        String query = "SELECT * FROM " + NAME_TABLE_ACCOUNT
                + " WHERE " + ACCOUNT_ID + " = " + accountId;
        try {
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    account = new Account(cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getInt(2),
                            cursor.getString(3),
                            cursor.getString(4),
                            cursor.getString(5),
                            cursor.getString(6),
                            cursor.getInt(7));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            AppUtils.handlerException(e);
        }

        db.close();
        return account;
    }


    /**
     * Hàm thêm tài khoản vào trong database
     *
     * @param account
     * @created_by nxduong on 29/1/2021
     */

    @Override
    public long insertAccount(Account account) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, account.getAccountName());
        values.put(MONEY_CURRENT, account.getCurrentAmount());
        values.put(TYPE_PATH, account.getAccountTypePath());
        values.put(TYPE_NAME, account.getAccountTypeName());
        values.put(MONEY_TYPE, account.getMoneyType());
        values.put(DESCRIPTION, account.getDescription());
        values.put(REPORT, account.getReport());

        long insert = DBUtils.checkDBFail;
        try {
            insert = db.insert(NAME_TABLE_ACCOUNT, null, values);
        } catch (Exception e) {
            AppUtils.handlerException(e);
        }
        db.close();
        return insert;
    }

    /**
     * Hàm cập nhật tài khoản vào trong database
     *
     * @param account
     * @created_by nxduong on 29/1/2021
     */
    @Override
    public long updateAccount(Account account) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, account.getAccountName());
        values.put(MONEY_CURRENT, account.getCurrentAmount());
        values.put(TYPE_PATH, account.getAccountTypePath());
        values.put(TYPE_NAME, account.getAccountTypeName());
        values.put(MONEY_TYPE, account.getMoneyType());
        values.put(DESCRIPTION, account.getDescription());
        values.put(REPORT, account.getReport());
        long update = DBUtils.checkDBFail;
        try {
            update = db.update(NAME_TABLE_ACCOUNT, values, ACCOUNT_ID + " = ? ",
                    new String[]{String.valueOf(account.getAccountId())});
        } catch (Exception e) {
            AppUtils.handlerException(e);
        }


        db.close();
        return update;
    }

    /**
     * Hàm xóa tài khoản trong database
     *
     * @param accountId
     * @created_by nxduong on 29/1/2021
     */

    @Override
    public long deleteAccount(int accountId) {
        db = this.getWritableDatabase();
        long delete = DBUtils.checkDBFail;
        try {
            delete = db.delete(NAME_TABLE_ACCOUNT, ACCOUNT_ID + " = ?",
                    new String[]{String.valueOf(accountId)});
        } catch (Exception e) {
            AppUtils.handlerException(e);
        }

        db.close();
        return delete;
    }

    /**
     * Hàm trừ tiền trong tài khoản
     *
     * @param accountId   id tài khoản
     * @param numberMoney số tiền
     * @created_by nxduong on 16/2/2021
     */

    @Override
    public long subtractMoneyOfAccount(int accountId, int numberMoney) {
        db = this.getReadableDatabase();
        db = this.getWritableDatabase();
        String querySelectMoneyCurrentAccount = "SELECT " + MONEY_CURRENT
                + " FROM " + NAME_TABLE_ACCOUNT + " WHERE " + ACCOUNT_ID + " = " + accountId;
        long update = DBUtils.checkDBFail;
        try {
            Cursor cursor = db.rawQuery(querySelectMoneyCurrentAccount, null);
            cursor.moveToNext();

            int moneyCurrentAccount = cursor.getInt(0);
            moneyCurrentAccount -= numberMoney;

            ContentValues values = new ContentValues();
            values.put(MONEY_CURRENT, moneyCurrentAccount);
            update = db.update(NAME_TABLE_ACCOUNT, values, ACCOUNT_ID + " = ? ",
                    new String[]{String.valueOf(accountId)});
        } catch (Exception e) {
            AppUtils.handlerException(e);
        }

        db.close();
        return update;
    }

    /**
     * Hàm cộng tiền trong tài khoản
     *
     * @param accountId   id tài khoản
     * @param numberMoney số tiền
     * @created_by nxduong on 16/2/2021
     */

    @Override
    public long plusMoneyOfAccount(int accountId, int numberMoney) {
        db = this.getReadableDatabase();
        db = this.getWritableDatabase();
        String querySelectMoneyCurrentAccount = "SELECT " + MONEY_CURRENT
                + " FROM " + NAME_TABLE_ACCOUNT + " WHERE " + ACCOUNT_ID + " = " + accountId;
        long update = DBUtils.checkDBFail;
        try {
            Cursor cursor = db.rawQuery(querySelectMoneyCurrentAccount, null);
            cursor.moveToNext();

            int moneyCurrentAccount = cursor.getInt(0);
            moneyCurrentAccount += numberMoney;

            ContentValues values = new ContentValues();
            values.put(MONEY_CURRENT, moneyCurrentAccount);
            update = db.update(NAME_TABLE_ACCOUNT, values, ACCOUNT_ID + " = ? ",
                    new String[]{String.valueOf(accountId)});
        } catch (Exception e) {
            AppUtils.handlerException(e);
        }

        db.close();
        return update;
    }
}
