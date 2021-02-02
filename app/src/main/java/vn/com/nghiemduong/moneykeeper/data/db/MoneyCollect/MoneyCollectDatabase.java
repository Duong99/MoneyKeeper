package vn.com.nghiemduong.moneykeeper.data.db.MoneyCollect;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import vn.com.nghiemduong.moneykeeper.data.db.account.AccountMoneyDatabase;

import vn.com.nghiemduong.moneykeeper.data.model.MoneyCollect;
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
    private final static String COLLECT_CATEGORY_NAME = "categoryName";
    private final static String COLLECT_CATEGORY_PATH = "categoryPath";
    private final static String COLLECT_ACCOUNT_NAME = "accountName";
    private final static String COLLECT_EXPLAIN = "explain";
    private final static String COLLECT_DATE = "date";
    private final static String COLLECT_TIME = "time";
    private final static String COLLECT_REPORT = "report";
    private final static String COLLECT_IMAGE = "image";

    private MoneyCollectDatabaseMvpView mMoneyCollectDatabaseMvpView;
    private SQLiteDatabase db;

    public MoneyCollectDatabase(@Nullable Context context,
                                MoneyCollectDatabaseMvpView moneyCollectDatabaseMvpView) {
        super(context, DBUtils.DB_NAME, null, DBUtils.DATABASE_VERSION);
        this.mMoneyCollectDatabaseMvpView = moneyCollectDatabaseMvpView;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * Hàm lấy danh sách thu tiền trong database
     *
     * @created_by nxduong on 2/2/2021
     */
    @Override
    public void getAllMoneyCollect() {
        db = this.getReadableDatabase();
        ArrayList<MoneyCollect> listAccount = new ArrayList<>();
        String query = "SELECT * FROM " + NAME_TABLE_COLLECT;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                MoneyCollect moneyCollect = new MoneyCollect(cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getInt(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getString(8),
                        cursor.getInt(9),
                        cursor.getBlob(10));

                listAccount.add(moneyCollect);
            } while (cursor.moveToNext());
        }
        db.close();
        mMoneyCollectDatabaseMvpView.getAllMoneyCollectResult(listAccount);
    }

    /**
     * Hàm thêm thu tiền vào trong database
     *
     * @param moneyCollect
     * @created_by nxduong on  2/2/2021
     */

    @Override
    public void insertMoneyCollect(MoneyCollect moneyCollect) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLLECT_ACCOUNT_ID, moneyCollect.getAccountId());
        values.put(COLLECT_AMOUNT_OF_MONEY, moneyCollect.getAmountOfMoney());
        values.put(COLLECT_CATEGORY_NAME, moneyCollect.getCategoryName());
        values.put(COLLECT_CATEGORY_PATH, moneyCollect.getCategoryPath());
        values.put(COLLECT_ACCOUNT_NAME, moneyCollect.getAccountName());
        values.put(COLLECT_EXPLAIN, moneyCollect.getExplain());
        values.put(COLLECT_DATE, moneyCollect.getDate());
        values.put(COLLECT_TIME, moneyCollect.getTime());
        values.put(COLLECT_REPORT, moneyCollect.getReport());
        values.put(COLLECT_IMAGE, moneyCollect.getImage());

        long insert = db.insert(NAME_TABLE_COLLECT, null, values);

        db.close();

        if (insert != DBUtils.check) {
            mMoneyCollectDatabaseMvpView.insertMoneyCollectSuccess();
        } else {
            mMoneyCollectDatabaseMvpView.insertMoneyCollectFail();
        }
    }

    /**
     * Hàm cập nhật thu tiền vào trong database
     *
     * @param moneyCollect
     * @created_by nxduong on
     */

    @Override
    public void updateMoneyCollect(MoneyCollect moneyCollect) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLLECT_ACCOUNT_ID, moneyCollect.getAccountId());
        values.put(COLLECT_AMOUNT_OF_MONEY, moneyCollect.getAmountOfMoney());
        values.put(COLLECT_CATEGORY_NAME, moneyCollect.getCategoryName());
        values.put(COLLECT_CATEGORY_PATH, moneyCollect.getCategoryPath());
        values.put(COLLECT_ACCOUNT_NAME, moneyCollect.getAccountName());
        values.put(COLLECT_EXPLAIN, moneyCollect.getExplain());
        values.put(COLLECT_DATE, moneyCollect.getDate());
        values.put(COLLECT_TIME, moneyCollect.getTime());
        values.put(COLLECT_REPORT, moneyCollect.getReport());
        values.put(COLLECT_IMAGE, moneyCollect.getImage());
        long update = db.update(NAME_TABLE_COLLECT, values, COLLECT_ID + " = ? ",
                new String[]{String.valueOf(moneyCollect.getCollectId())});

        db.close();

        if (update != DBUtils.check) {
            mMoneyCollectDatabaseMvpView.updateMoneyCollectSuccess();
        } else {
            mMoneyCollectDatabaseMvpView.updateMoneyCollectFail();
        }
    }

    /**
     * Hàm xóa thu tiền trong database
     *
     * @param moneyCollectId
     * @created_by nxduong on 2/2/2021
     */

    @Override
    public void deleteMoneyCollect(int moneyCollectId) {
        db = this.getWritableDatabase();
        long delete = db.delete(NAME_TABLE_COLLECT, COLLECT_ID + " = ?",
                new String[]{String.valueOf(moneyCollectId)});

        if (delete != DBUtils.check) {
            mMoneyCollectDatabaseMvpView.deleteMoneyCollectSuccess();
        } else {
            mMoneyCollectDatabaseMvpView.deleteMoneyCollectFail();
        }
    }
}
