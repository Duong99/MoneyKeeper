package vn.com.nghiemduong.moneykeeper.data.db.moneyCollect;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.google.common.collect.Table;

import java.util.ArrayList;

import vn.com.nghiemduong.moneykeeper.data.db.account.AccountMoneyDatabase;

import vn.com.nghiemduong.moneykeeper.data.db.category.CategoryDatabase;
import vn.com.nghiemduong.moneykeeper.data.db.category.SubCategoryDatabase;
import vn.com.nghiemduong.moneykeeper.data.model.MoneyCollect;
import vn.com.nghiemduong.moneykeeper.utils.AppUtils;
import vn.com.nghiemduong.moneykeeper.utils.DBUtils;

/**
 * Lớp thực hiện thêm sửa xóa table MoneyCollect (Thu tiền) trong database
 * - @created_by nxduong on 2/2/2021
 **/
public class MoneyCollectDatabase extends SQLiteOpenHelper implements MoneyCollectDatabaseMvpPresenter {

    private final static String NAME_TABLE_COLLECT = "tb_MoneyCollect";
    private final static String COLLECT_ID = "collectId";
    private final static String COLLECT_ACCOUNT_ID = AccountMoneyDatabase.ACCOUNT_ID;
    private final static String COLLECT_AMOUNT_OF_MONEY = "amountOfMoney";
    private final static String CATEGORY_ID = CategoryDatabase.CATEGORY_ID;
    private final static String SUB_CATEGORY_ID = SubCategoryDatabase.SUB_ID;
    private final static String COLLECT_EXPLAIN = "explain";
    private final static String COLLECT_DATE = "date";
    private final static String COLLECT_TIME = "time";
    private final static String COLLECT_REPORT = "report";
    private final static String COLLECT_IMAGE = "image";

    private Context mContext;

    private SQLiteDatabase db;

    public MoneyCollectDatabase(@Nullable Context context) {
        super(context, DBUtils.DB_NAME, null, DBUtils.DATABASE_VERSION);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * Hàm lấy danh sách thu tiền bảng MoneyCollect trong database
     *
     * @created_by nxduong on 2/2/2021
     */
    @Override
    public ArrayList<MoneyCollect> getAllMoneyCollect() {
        db = this.getReadableDatabase();
        ArrayList<MoneyCollect> listMoneyCollect = new ArrayList<>();
        String query = "SELECT * FROM " + NAME_TABLE_COLLECT;
        try {
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    MoneyCollect moneyCollect = new MoneyCollect(cursor.getInt(0),
                            cursor.getInt(1),
                            cursor.getInt(2),
                            cursor.getInt(3),
                            cursor.getInt(4),
                            cursor.getString(5),
                            cursor.getString(6),
                            cursor.getString(7),
                            cursor.getInt(8),
                            cursor.getBlob(9));

                    listMoneyCollect.add(moneyCollect);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            AppUtils.handlerException(e);
        }
        db.close();
        return listMoneyCollect;
    }

    /**
     * -  Hàm tìm kiếm theo thời gian
     *
     * @param fromDate từ ngày
     * @param fromDate đến ngày
     *                 - @created_by nxduong on 19/2/2021
     **/

    @Override
    public ArrayList<MoneyCollect> searchMoneyCollectByDate(String fromDate, String toDate) {
        db = this.getWritableDatabase();
        ArrayList<MoneyCollect> listMoneyCollect = new ArrayList<>();
        String query;
        if (toDate.equals("")) {
            query = "SELECT * FROM " + NAME_TABLE_COLLECT
                    + " WHERE " + COLLECT_DATE + " = '" + fromDate + "'";
        } else {
            query = "SELECT * FROM " + NAME_TABLE_COLLECT
                    + " WHERE " + COLLECT_DATE + " between '" + fromDate + "' and '" + toDate + "'";
        }

        try {
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    MoneyCollect moneyCollect = new MoneyCollect(cursor.getInt(0),
                            cursor.getInt(1),
                            cursor.getInt(2),
                            cursor.getInt(3),
                            cursor.getInt(4),
                            cursor.getString(5),
                            cursor.getString(6),
                            cursor.getString(7),
                            cursor.getInt(8),
                            cursor.getBlob(9));

                    listMoneyCollect.add(moneyCollect);
                } while (cursor.moveToNext());
            }

        } catch (Exception e) {
            AppUtils.handlerException(e);
        }
        return listMoneyCollect;
    }

    /**
     * Hàm thêm thu tiền trong bảng MoneyCollect vào trong database
     * và cập nhật lại tiền hiệ tại trong bảng tài khoản (Account)
     *
     * @param moneyCollect
     * @created_by nxduong on  2/2/2021
     */

    @Override
    public long insertMoneyCollect(MoneyCollect moneyCollect) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLLECT_ACCOUNT_ID, moneyCollect.getAccountId());
        values.put(COLLECT_AMOUNT_OF_MONEY, moneyCollect.getAmountOfMoney());
        values.put(CATEGORY_ID, moneyCollect.getCategoryId());
        values.put(SUB_CATEGORY_ID, moneyCollect.getSubCategoryId());
        values.put(COLLECT_EXPLAIN, moneyCollect.getExplain());
        values.put(COLLECT_DATE, moneyCollect.getDate());
        values.put(COLLECT_TIME, moneyCollect.getTime());
        values.put(COLLECT_REPORT, moneyCollect.getReport());
        values.put(COLLECT_IMAGE, moneyCollect.getImage());
        long insert = DBUtils.checkDBFail;

        try {
            insert = db.insert(NAME_TABLE_COLLECT, null, values);

            if (insert != DBUtils.checkDBFail) {
                new AccountMoneyDatabase(mContext)
                        .plusMoneyOfAccount(moneyCollect.getAccountId(),
                                moneyCollect.getAmountOfMoney());
            }
        } catch (Exception e) {
            AppUtils.handlerException(e);
        }

        return insert;
    }

    /**
     * Hàm cập nhật thu tiền vào bảng MoneyCollect trong database
     * và cập nhật lại tiền hiệ tại trong bảng tài khoản (Account)
     *
     * @param moneyCollect
     * @created_by nxduong on 2/2/2021
     */

    @Override
    public long updateMoneyCollect(MoneyCollect moneyCollect, int numberMoneyPrevious) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLLECT_ACCOUNT_ID, moneyCollect.getAccountId());
        values.put(COLLECT_AMOUNT_OF_MONEY, moneyCollect.getAmountOfMoney());
        values.put(CATEGORY_ID, moneyCollect.getCategoryId());
        values.put(SUB_CATEGORY_ID, moneyCollect.getSubCategoryId());
        values.put(COLLECT_EXPLAIN, moneyCollect.getExplain());
        values.put(COLLECT_DATE, moneyCollect.getDate());
        values.put(COLLECT_TIME, moneyCollect.getTime());
        values.put(COLLECT_REPORT, moneyCollect.getReport());
        values.put(COLLECT_IMAGE, moneyCollect.getImage());
        long update = DBUtils.checkDBFail;
        try {
            update = db.update(NAME_TABLE_COLLECT, values, COLLECT_ID + " = ? ",
                    new String[]{String.valueOf(moneyCollect.getCollectId())});

            if (update != DBUtils.checkDBFail) {
                updateMoneyOfAccountWhenUpdateCollect(moneyCollect.getAccountId(),
                        moneyCollect.getAmountOfMoney(), numberMoneyPrevious);
            }
        } catch (Exception e) {
            AppUtils.handlerException(e);
        }
        db.close();
        return update;
    }

    /**
     * Hàm xóa thu tiền ở bảng MoneyCollect trong database
     * và cập nhật lại tiền hiệ tại trong bảng tài khoản (Account)
     *
     * @param moneyCollect đối tượng thu tiền cần xóa
     * @created_by nxduong on 2/2/2021
     */

    @Override
    public long deleteMoneyCollect(MoneyCollect moneyCollect) {
        db = this.getWritableDatabase();
        long delete = DBUtils.checkDBFail;
        try {
            delete = db.delete(NAME_TABLE_COLLECT, COLLECT_ID + " = ?",
                    new String[]{String.valueOf(moneyCollect.getCollectId())});

            if (delete != DBUtils.checkDBFail) {
                // Trừ tiền trong tài khoản
                new AccountMoneyDatabase(mContext)
                        .subtractMoneyOfAccount(moneyCollect.getAccountId(),
                                moneyCollect.getAmountOfMoney());
            }
        } catch (Exception e) {
            AppUtils.handlerException(e);
        }
        return delete;
    }

    /**
     * Hàm có tác dụng cập nhật lại số tiền tiền table Tài khoản khi người dùng update thu tiền
     *
     * @param accountId, numberMoneyCurrent, numberMoneyPrevious
     * @created_by nxduong on 6/2/2021
     */
    @Override
    public long updateMoneyOfAccountWhenUpdateCollect(int accountId, int numberMoneyCurrent,
                                                      int numberMoneyPrevious) {
        db = this.getReadableDatabase();
        db = this.getWritableDatabase();
        String querySelectMoneyCurrentAccount = "SELECT " + AccountMoneyDatabase.ACCOUNT_MONEY_CURRENT
                + " FROM " + AccountMoneyDatabase.NAME_TABLE_ACCOUNT
                + " WHERE " + AccountMoneyDatabase.ACCOUNT_ID + " = " + accountId;
        long update = DBUtils.checkDBFail;
        try {
            Cursor cursor = db.rawQuery(querySelectMoneyCurrentAccount, null);
            cursor.moveToNext();
            int moneyCurrentAccount = cursor.getInt(0);

            moneyCurrentAccount -= numberMoneyPrevious;
            moneyCurrentAccount += numberMoneyCurrent;

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
