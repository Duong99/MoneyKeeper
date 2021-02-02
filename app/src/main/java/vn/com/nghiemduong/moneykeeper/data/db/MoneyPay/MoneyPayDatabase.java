package vn.com.nghiemduong.moneykeeper.data.db.MoneyPay;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import vn.com.nghiemduong.moneykeeper.data.db.account.AccountMoneyDatabase;
import vn.com.nghiemduong.moneykeeper.data.model.MoneyPay;
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
    private final static String PAY_CATEGORY_NAME = "categoryName";
    private final static String PAY_CATEGORY_PATH = "categoryPath";
    private final static String PAY_ACCOUNT_NAME = "accountName";
    private final static String PAY_EXPLAIN = "explain";
    private final static String PAY_DATE = "date";
    private final static String PAY_TIME = "time";
    private final static String PAY_REPORT = "report";
    private final static String PAY_IMAGE = "image";

    private SQLiteDatabase db;
    private MoneyPayDatabaseMvpView mMoneyPayDatabaseMvpView;

    public MoneyPayDatabase(@Nullable Context context, MoneyPayDatabaseMvpView moneyPayDatabaseMvpView) {
        super(context, DBUtils.DB_NAME, null, DBUtils.DATABASE_VERSION);
        this.mMoneyPayDatabaseMvpView = moneyPayDatabaseMvpView;
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
    public void getAllMoneyPay() {

    }

    /**
     * Hàm thêm chi tiền vào trong database
     *
     * @param moneyPay
     * @created_by nxduong on  2/2/2021
     */

    @Override
    public void insertMoneyPay(MoneyPay moneyPay) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PAY_ACCOUNT_ID, moneyPay.getAccountId());
        values.put(PAY_AMOUNT_OF_MONEY, moneyPay.getAmountOfMoney());
        values.put(PAY_CATEGORY_NAME, moneyPay.getCategoryName());
        values.put(PAY_CATEGORY_PATH, moneyPay.getCategoryPath());
        values.put(PAY_ACCOUNT_NAME, moneyPay.getAccountName());
        values.put(PAY_EXPLAIN, moneyPay.getExplain());
        values.put(PAY_DATE, moneyPay.getDate());
        values.put(PAY_TIME, moneyPay.getTime());
        values.put(PAY_REPORT, moneyPay.getReport());
        values.put(PAY_IMAGE, moneyPay.getImage());

        long insert = db.insert(NAME_TABLE_PAY, null, values);

        db.close();

        if (insert != DBUtils.check) {
            mMoneyPayDatabaseMvpView.insertMoneyPaySuccess();
        } else {
            mMoneyPayDatabaseMvpView.insertMoneyPayFail();
        }
    }

    /**
     * Hàm sửa chi tiền vào trong database
     *
     * @param moneyPay
     * @created_by nxduong on  2/2/2021
     */

    @Override
    public void updateMoneyPay(MoneyPay moneyPay) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PAY_ACCOUNT_ID, moneyPay.getAccountId());
        values.put(PAY_AMOUNT_OF_MONEY, moneyPay.getAmountOfMoney());
        values.put(PAY_CATEGORY_NAME, moneyPay.getCategoryName());
        values.put(PAY_CATEGORY_PATH, moneyPay.getCategoryPath());
        values.put(PAY_ACCOUNT_NAME, moneyPay.getAccountName());
        values.put(PAY_EXPLAIN, moneyPay.getExplain());
        values.put(PAY_DATE, moneyPay.getDate());
        values.put(PAY_TIME, moneyPay.getTime());
        values.put(PAY_REPORT, moneyPay.getReport());
        values.put(PAY_IMAGE, moneyPay.getImage());

        long update = db.update(NAME_TABLE_PAY, values, PAY_ID + " = ? ",
                new String[]{String.valueOf(moneyPay.getPayId())});

        db.close();

        if (update != DBUtils.check) {
            mMoneyPayDatabaseMvpView.updateMoneyPaySuccess();
        } else {
            mMoneyPayDatabaseMvpView.updateMoneyPayFail();
        }
    }

    /**
     * Hàm xóa chi tiền vào trong database
     *
     * @created_by nxduong on  2/2/2021
     */

    @Override
    public void deleteMoneyPay(int moneyPayId) {
        db = this.getWritableDatabase();
        long delete = db.delete(NAME_TABLE_PAY, PAY_ID + " = ?",
                new String[]{String.valueOf(moneyPayId)});

        if (delete != DBUtils.check) {
            mMoneyPayDatabaseMvpView.deleteMoneyPaySuccess();
        } else {
            mMoneyPayDatabaseMvpView.deleteMoneyPayFail();
        }
    }
}
