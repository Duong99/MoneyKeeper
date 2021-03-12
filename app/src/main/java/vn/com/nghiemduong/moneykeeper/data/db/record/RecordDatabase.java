package vn.com.nghiemduong.moneykeeper.data.db.record;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import vn.com.nghiemduong.moneykeeper.data.db.BaseSqLite;
import vn.com.nghiemduong.moneykeeper.data.db.account.AccountDatabase;
import vn.com.nghiemduong.moneykeeper.data.db.category.CategoryDatabase;
import vn.com.nghiemduong.moneykeeper.data.model.db.Record;
import vn.com.nghiemduong.moneykeeper.utils.AppUtils;
import vn.com.nghiemduong.moneykeeper.utils.DBUtils;

/**
 * - * Lớp thực hiện thêm sửa xóa table Pay trong database chi các kiểu :
 * Chi tiền, thu tiền, cho vay, đi vay, trả nợ, thu nợ
 * <p>
 * - @created_by nxduong on 2/2/2021
 **/
public class RecordDatabase extends BaseSqLite implements RecordDatabaseMvpPresenter {

    private final static String NAME_TABLE_RECORD = "tb_Record";
    public final static String RECORD_ID = "recordId";
    private final static String AMOUNT = "amount";
    private final static String CATEGORY_ID = CategoryDatabase.CATEGORY_ID;
    private final static String DEBTOR = "debtor";
    private final static String EXPLAIN = "explain";
    private final static String DATE = "date";
    private final static String TIME = "time";
    private final static String ACCOUNT_ID = AccountDatabase.ACCOUNT_ID;
    private final static String TO_ACCOUNT_ID = "toAccountId";
    private final static String DATE_DURATION = "dateDuration";
    private final static String REPORT = "report";
    private final static String IMAGE = "image";
    private final static String TYPE = "type";


    private SQLiteDatabase db;
    private Context mContext;


    public RecordDatabase(@Nullable Context context) {
        super(context);
        this.mContext = context;
    }

    /**
     * Hàm lấy danh sách chi tiền trong database
     *
     * @created_by nxduong on 2/2/2021
     */
    @Override
    public ArrayList<Record> getAllRecord() {
        db = this.getReadableDatabase();
        ArrayList<Record> listRecord = new ArrayList<>();
        String query = "SELECT * FROM " + NAME_TABLE_RECORD;
        try {
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    Record Record = new Record(cursor.getInt(0),
                            cursor.getInt(1),
                            cursor.getInt(2),
                            cursor.getString(3),
                            cursor.getString(4),
                            cursor.getString(5),
                            cursor.getString(6),
                            cursor.getInt(7),
                            cursor.getInt(8),
                            cursor.getString(9),
                            cursor.getInt(10),
                            cursor.getBlob(11),
                            cursor.getInt(12));
                    listRecord.add(Record);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            AppUtils.handlerException(e);
        }
        db.close();
        return listRecord;
    }

    /**
     * -  Hàm tìm kiếm theo thời gian
     *
     * @param fromDate từ ngày
     * @param fromDate đến ngày
     *                 - @created_by nxduong on 19/2/2021
     **/

    @Override
    public ArrayList<Record> searchRecordByDate(String fromDate, String toDate) {
        db = this.getWritableDatabase();
        ArrayList<Record> listRecord = new ArrayList<>();
        String query;
        if (toDate.equals("")) {
            query = "SELECT * FROM " + NAME_TABLE_RECORD
                    + " WHERE " + DATE + " = '" + fromDate + "'";
        } else {
            query = "SELECT * FROM " + NAME_TABLE_RECORD
                    + " WHERE " + DATE + " between '" + fromDate + "' and '" + toDate + "'";
        }

        try {
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    Record Record = new Record(cursor.getInt(0),
                            cursor.getInt(1),
                            cursor.getInt(2),
                            cursor.getString(3),
                            cursor.getString(4),
                            cursor.getString(5),
                            cursor.getString(6),
                            cursor.getInt(7),
                            cursor.getInt(8),
                            cursor.getString(9),
                            cursor.getInt(10),
                            cursor.getBlob(11),
                            cursor.getInt(11));

                    listRecord.add(Record);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            AppUtils.handlerException(e);
        }
        return listRecord;
    }

    @Override
    public ArrayList<Record> getAllRecordWhereType(int type) {
        db = this.getReadableDatabase();
        ArrayList<Record> listRecord = new ArrayList<>();
        String query = "SELECT * FROM " + NAME_TABLE_RECORD + " WHERE " + TYPE + " = " + type;
        try {
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    Record Record = new Record(cursor.getInt(0),
                            cursor.getInt(1),
                            cursor.getInt(2),
                            cursor.getString(3),
                            cursor.getString(4),
                            cursor.getString(5),
                            cursor.getString(6),
                            cursor.getInt(7),
                            cursor.getInt(8),
                            cursor.getString(9),
                            cursor.getInt(10),
                            cursor.getBlob(11),
                            cursor.getInt(12));
                    listRecord.add(Record);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            AppUtils.handlerException(e);
        }
        db.close();
        return listRecord;
    }

    /**
     * Hàm thêm chi tiền vào bảng Record trong database
     * và cập nhật lại tiền hiệ tại trong bảng tài khoản (Account)
     *
     * @param record đối tượng chi tiền, thu tiền, cho vay, ....
     * @created_by nxduong on  2/2/2021
     */

    @Override
    public long insertRecord(Record record) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(AMOUNT, record.getAmount());
        values.put(CATEGORY_ID, record.getCategoryId());
        values.put(DEBTOR, record.getDebtor());
        values.put(EXPLAIN, record.getExplain());
        values.put(DATE, record.getDate());
        values.put(TIME, record.getTime());
        values.put(ACCOUNT_ID, record.getAccountId());
        values.put(TO_ACCOUNT_ID, record.getToAccountId());
        values.put(DATE_DURATION, record.getDateDuration());
        values.put(REPORT, record.getReport());
        values.put(IMAGE, record.getImage());
        values.put(TYPE, record.getType());

        long insert = DBUtils.checkDBFail;
        try {
            insert = db.insert(NAME_TABLE_RECORD, null, values);
            if (insert != DBUtils.checkDBFail) {
                // Xóa tiền trong tài khoản khi chi tiền

            }
        } catch (Exception e) {
            AppUtils.handlerException(e);
        }
        db.close();
        return insert;
    }

    /**
     * Hàm sửa chi tiền ở bảng Record trong database
     * và cập nhật lại tiền hiệ tại trong bảng tài khoản (Account)
     *
     * @param record
     * @created_by nxduong on 2/2/2021
     */

    @Override
    public long updateRecord(Record record, int numberMoneyPrevious) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(AMOUNT, record.getAmount());
        values.put(CATEGORY_ID, record.getCategoryId());
        values.put(DEBTOR, record.getDebtor());
        values.put(EXPLAIN, record.getExplain());
        values.put(DATE, record.getDate());
        values.put(TIME, record.getTime());
        values.put(ACCOUNT_ID, record.getAccountId());
        values.put(TO_ACCOUNT_ID, record.getToAccountId());
        values.put(DATE_DURATION, record.getDateDuration());
        values.put(REPORT, record.getReport());
        values.put(IMAGE, record.getImage());
        values.put(TYPE, record.getType());

        long update = DBUtils.checkDBFail;
        try {
            update = db.update(NAME_TABLE_RECORD, values, RECORD_ID + " = ? ",
                    new String[]{String.valueOf(record.getRecordId())});

            if (update != DBUtils.checkDBFail) {
                // Cập nhật lại tiền trong tài khoản khi thay đổi số tiền chi

            }
        } catch (Exception e) {
            AppUtils.handlerException(e);
        }
        return update;
    }

    /**
     * Hàm xóa chi tiền vào bảng Record trong database
     * và cập nhật lại tiền hiệ tại trong bảng tài khoản (Account)
     *
     * @created_by nxduong on  2/2/2021
     */

    @Override
    public long deleteRecord(Record record) {
        db = this.getWritableDatabase();

        long delete = DBUtils.checkDBFail;
        try {
            delete = db.delete(NAME_TABLE_RECORD, RECORD_ID + " = ?",
                    new String[]{String.valueOf(record.getRecordId())});
            if (delete != DBUtils.checkDBFail) {
                // Cộng lại tiền vào tài khoản khi xóa chi tiền


            }
        } catch (Exception e) {
            AppUtils.handlerException(e);
        }
        return delete;
    }
}
