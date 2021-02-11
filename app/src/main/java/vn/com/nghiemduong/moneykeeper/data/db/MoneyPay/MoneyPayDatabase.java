package vn.com.nghiemduong.moneykeeper.data.db.MoneyPay;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWindow;
import android.database.sqlite.SQLiteClosable;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import vn.com.nghiemduong.moneykeeper.data.db.account.AccountMoneyDatabase;
import vn.com.nghiemduong.moneykeeper.data.db.category.CategoryDatabase;
import vn.com.nghiemduong.moneykeeper.data.db.category.SubCategoryDatabase;
import vn.com.nghiemduong.moneykeeper.data.model.MoneyCollect;
import vn.com.nghiemduong.moneykeeper.data.model.MoneyPay;
import vn.com.nghiemduong.moneykeeper.utils.AppUtils;
import vn.com.nghiemduong.moneykeeper.utils.DBUtils;

/**
 * - * Lớp thực hiện thêm sửa xóa table MoneyPay (Chi tiền) trong database
 * <p>
 * <p>
 * - @created_by nxduong on 2/2/2021
 **/
public class MoneyPayDatabase extends SQLiteOpenHelper implements MoneyPayDatabaseMvpPresenter {

    private final static String NAME_TABLE_PAY = "tb_MoneyPay";
    private final static String PAY_ID = "PAYId";
    private final static String PAY_ACCOUNT_ID = AccountMoneyDatabase.ACCOUNT_ID;
    private final static String PAY_AMOUNT_OF_MONEY = "amountOfMoney";
    private final static String CATEGORY_ID = CategoryDatabase.CATEGORY_ID;
    private final static String SUB_CATEGORY_ID = SubCategoryDatabase.SUB_ID;
    private final static String PAY_EXPLAIN = "explain";
    private final static String PAY_DATE = "date";
    private final static String PAY_TIME = "time";
    private final static String PAY_REPORT = "report";
    private final static String PAY_IMAGE = "image";

    private SQLiteDatabase db;


    public MoneyPayDatabase(@Nullable Context context) {
        super(context, DBUtils.DB_NAME, null, DBUtils.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    /**
     * Hàm lấy danh sách chi tiền trong database
     *
     * @created_by nxduong on 2/2/2021
     */
    @Override
    public ArrayList<MoneyPay> getAllMoneyPay() {
        db = this.getReadableDatabase();
        ArrayList<MoneyPay> listMoneyPay = new ArrayList<>();
        String query = "SELECT * FROM " + NAME_TABLE_PAY;
        try {
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    MoneyPay moneyPay = new MoneyPay(cursor.getInt(0),
                            cursor.getInt(1),
                            cursor.getInt(2),
                            cursor.getInt(3),
                            cursor.getInt(4),
                            cursor.getString(5),
                            cursor.getString(6),
                            cursor.getString(7),
                            cursor.getInt(8),
                            cursor.getBlob(9));
                    listMoneyPay.add(moneyPay);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            AppUtils.handlerException(e);
        }
        db.close();
        return listMoneyPay;
    }

    /**
     * Hàm thêm chi tiền vào bảng MoneyPay trong database
     * và cập nhật lại tiền hiệ tại trong bảng tài khoản (Account)
     *
     * @param moneyPay
     * @created_by nxduong on  2/2/2021
     */

    @Override
    public long insertMoneyPay(MoneyPay moneyPay) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PAY_ACCOUNT_ID, moneyPay.getAccountId());
        values.put(PAY_AMOUNT_OF_MONEY, moneyPay.getAmountOfMoney());
        values.put(CATEGORY_ID, moneyPay.getCategoryId());
        values.put(SUB_CATEGORY_ID, moneyPay.getSubCategoryId());
        values.put(PAY_EXPLAIN, moneyPay.getExplain());
        values.put(PAY_DATE, moneyPay.getDate());
        values.put(PAY_TIME, moneyPay.getTime());
        values.put(PAY_REPORT, moneyPay.getReport());
        values.put(PAY_IMAGE, moneyPay.getImage());

        long insert = db.insert(NAME_TABLE_PAY, null, values);
        if (insert == DBUtils.checkDBFail) {
            db.close();
            return insert;
        } else {
            long update = updateMoneyOfAccountWhenInsertPay(moneyPay.getAccountId(),
                    moneyPay.getAmountOfMoney());
            db.close();
            return update;
        }
    }

    /**
     * Hàm sửa chi tiền ở bảng MoneyPay trong database
     * và cập nhật lại tiền hiệ tại trong bảng tài khoản (Account)
     *
     * @param moneyPay
     * @created_by nxduong on  2/2/2021
     */

    @Override
    public long updateMoneyPay(MoneyPay moneyPay, int numberMoneyPrevious) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PAY_ACCOUNT_ID, moneyPay.getAccountId());
        values.put(PAY_AMOUNT_OF_MONEY, moneyPay.getAmountOfMoney());
        values.put(CATEGORY_ID, moneyPay.getCategoryId());
        values.put(SUB_CATEGORY_ID, moneyPay.getSubCategoryId());
        values.put(PAY_EXPLAIN, moneyPay.getExplain());
        values.put(PAY_DATE, moneyPay.getDate());
        values.put(PAY_TIME, moneyPay.getTime());
        values.put(PAY_REPORT, moneyPay.getReport());
        values.put(PAY_IMAGE, moneyPay.getImage());

        long update = db.update(NAME_TABLE_PAY, values, PAY_ID + " = ? ",
                new String[]{String.valueOf(moneyPay.getPayId())});

        if (update == DBUtils.checkDBFail) {
            db.close();
            return update;
        } else {
            long updateAccount = updateMoneyOfAccountWhenUpdatePay(moneyPay.getAccountId(),
                    moneyPay.getAmountOfMoney(), numberMoneyPrevious);
            db.close();
            return updateAccount;
        }

    }

    /**
     * Hàm xóa chi tiền vào bảng MoneyPay trong database
     * và cập nhật lại tiền hiệ tại trong bảng tài khoản (Account)
     *
     * @created_by nxduong on  2/2/2021
     */

    @Override
    public long deleteMoneyPay(MoneyPay moneyPay) {
        db = this.getWritableDatabase();
        long delete = db.delete(NAME_TABLE_PAY, PAY_ID + " = ?",
                new String[]{String.valueOf(moneyPay.getPayId())});

        if (delete == DBUtils.checkDBFail) {
            db.close();
            return delete;
        } else {
            long update = updateMoneyOfAccountWhenDeletePay(moneyPay.getAccountId(),
                    moneyPay.getAmountOfMoney());
            db.close();
            return update;
        }
    }

    /**
     * Cập nhật lại số tiền hiện tại trong tài khoản khi thêm trường chi tiêu
     *
     * @param accountId, numberMoney
     * @created_by nxduong on 3/2/2021
     */

    @Override
    public long updateMoneyOfAccountWhenInsertPay(int accountId, int numberMoney) {
        db = this.getReadableDatabase();
        db = this.getWritableDatabase();
        String querySelectMoneyCurrentAccount = "SELECT " + AccountMoneyDatabase.ACCOUNT_MONEY_CURRENT
                + " FROM " + AccountMoneyDatabase.NAME_TABLE_ACCOUNT
                + " WHERE " + AccountMoneyDatabase.ACCOUNT_ID + " = " + accountId;

        Cursor cursor = db.rawQuery(querySelectMoneyCurrentAccount, null);
        cursor.moveToNext();
        int moneyCurrentAccount = cursor.getInt(0);
        moneyCurrentAccount -= numberMoney;

        ContentValues values = new ContentValues();
        values.put(AccountMoneyDatabase.ACCOUNT_MONEY_CURRENT, moneyCurrentAccount);
        long update = db.update(AccountMoneyDatabase.NAME_TABLE_ACCOUNT, values,
                AccountMoneyDatabase.ACCOUNT_ID + " = ? ",
                new String[]{String.valueOf(accountId)});
        db.close();
        return update;
    }

    /**
     * @param accountId id tài khoản, numberMoney số tiền trong chi tiền bi xóa
     * @created_by nxduong on 5/2/2021
     */

    @Override
    public long updateMoneyOfAccountWhenDeletePay(int accountId, int numberMoney) {
        db = this.getReadableDatabase();
        db = this.getWritableDatabase();
        String querySelectMoneyCurrentAccount = "SELECT " + AccountMoneyDatabase.ACCOUNT_MONEY_CURRENT
                + " FROM " + AccountMoneyDatabase.NAME_TABLE_ACCOUNT
                + " WHERE " + AccountMoneyDatabase.ACCOUNT_ID + " = " + accountId;
        long update = -1;
        try {
            Cursor cursor = db.rawQuery(querySelectMoneyCurrentAccount, null);
            cursor.moveToNext();
            int moneyCurrentAccount = cursor.getInt(0);
            moneyCurrentAccount += numberMoney;
            ContentValues values = new ContentValues();
            values.put(AccountMoneyDatabase.ACCOUNT_MONEY_CURRENT, moneyCurrentAccount);
            update = db.update(AccountMoneyDatabase.NAME_TABLE_ACCOUNT, values,
                    AccountMoneyDatabase.ACCOUNT_ID + " = ? ",
                    new String[]{String.valueOf(accountId)});
        } catch (Exception e) {
            AppUtils.handlerException(e);
        }

        db.close();
        return update;
    }

    /**
     * Hàm có tác dụng cập nhật lại số tiền tiền table Tài khoản khi người dùng cập nhật chi tiền
     *
     * @param accountId, numberMoneyCurrent, numberMoneyPrevious
     * @created_by nxduong on 6/2/2021
     */

    @Override
    public long updateMoneyOfAccountWhenUpdatePay(int accountId, int numberMoneyCurrent,
                                                  int numberMoneyPrevious) {

        db = this.getReadableDatabase();
        db = this.getWritableDatabase();
        String querySelectMoneyCurrentAccount = "SELECT " + AccountMoneyDatabase.ACCOUNT_MONEY_CURRENT
                + " FROM " + AccountMoneyDatabase.NAME_TABLE_ACCOUNT
                + " WHERE " + AccountMoneyDatabase.ACCOUNT_ID + " = " + accountId;
        long update = -1;
        try {
            Cursor cursor = db.rawQuery(querySelectMoneyCurrentAccount, null);
            cursor.moveToNext();
            int moneyCurrentAccount = cursor.getInt(0);

            moneyCurrentAccount += numberMoneyPrevious;
            moneyCurrentAccount -= numberMoneyCurrent;

            ContentValues values = new ContentValues();
            values.put(AccountMoneyDatabase.ACCOUNT_MONEY_CURRENT, moneyCurrentAccount);
            update = db.update(AccountMoneyDatabase.NAME_TABLE_ACCOUNT, values,
                    AccountMoneyDatabase.ACCOUNT_ID + " = ? ",
                    new String[]{String.valueOf(accountId)});
        } catch (Exception e) {
            AppUtils.handlerException(e);
        }

        db.close();
        return update;
    }
}
