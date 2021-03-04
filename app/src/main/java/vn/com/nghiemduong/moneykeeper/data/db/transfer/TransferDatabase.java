package vn.com.nghiemduong.moneykeeper.data.db.transfer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import vn.com.nghiemduong.moneykeeper.data.db.BaseSqLite;
import vn.com.nghiemduong.moneykeeper.data.db.account.AccountMoneyDatabase;

import vn.com.nghiemduong.moneykeeper.data.db.moneyPay.PayDatabase;
import vn.com.nghiemduong.moneykeeper.data.model.db.Transfer;
import vn.com.nghiemduong.moneykeeper.utils.AppUtils;
import vn.com.nghiemduong.moneykeeper.utils.DBUtils;

/**
 * - Lấy thực hiện việc thêm sửa xóa trong tb_Transfer
 * - @created_by nxduong on 16/2/2021
 **/
public class TransferDatabase extends BaseSqLite implements TransferDatabaseMvpPresenter {
    private final static String NAME_TABLE_TRANSFER = "tb_Transfer";
    private final static String TRANSFER_ID = "transferId";
    private final static String AMOUNT = "amount";
    private final static String FROM_ACCOUNT_ID = "fromAccountId";
    private final static String TO_ACCOUNT_ID = "toAccountId";
    private final static String DATE = "date";
    private final static String TIME = "time";
    private final static String EXPLAIN = "explain";
    //private final static String PAY_ID = PayDatabase.PAY_ID;
    private final static String REPORT = "report";
    private final static String IMAGE = "image";
    private final static String STATUS = "status";

    private SQLiteDatabase db;
    private Context mContext;

    public TransferDatabase(@Nullable Context context) {
        super(context);
        this.mContext = context;
    }

    /**
     * Hàm đọc lấy hết dữ liệu trong tb_transfer
     *
     * @return transfers danh sách chuyển tiền
     * @created_by nxduong on 16/2/2021
     * @see
     */

    @Override
    public ArrayList<Transfer> getAllTransfer() {
        db = this.getReadableDatabase();
        ArrayList<Transfer> transfers = new ArrayList<>();
        String query = "SELECT * FROM " + NAME_TABLE_TRANSFER;

        try {
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    Transfer transfer = new Transfer(cursor.getInt(0),
                            cursor.getInt(1),
                            cursor.getInt(2),
                            cursor.getInt(3),
                            cursor.getString(4),
                            cursor.getString(5),
                            cursor.getString(6),
                            cursor.getInt(7),
                            cursor.getBlob(8),
                            cursor.getInt(9));
                    transfers.add(transfer);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            AppUtils.handlerException(e);
        }
        db.close();
        return transfers;
    }

    /**
     * hàm thêm chuyển tiền
     *
     * @param transfer đối tượng chuyển tiền
     * @created_by nxduong on 16/2/2021
     */

    @Override
    public long insertTransfer(Transfer transfer) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(AMOUNT, transfer.getAmount());
        values.put(FROM_ACCOUNT_ID, transfer.getFromAccountId());
        values.put(TO_ACCOUNT_ID, transfer.getToAccountId());
        values.put(DATE, transfer.getDate());
        values.put(TIME, transfer.getTime());
        values.put(EXPLAIN, transfer.getExplain());
        values.put(REPORT, transfer.getReport());
        values.put(IMAGE, transfer.getImage());
        values.put(STATUS, transfer.getStatus());

        try {
            // Thêm vào bảng chuyển tiền (tb_Transfer)
            long insert = db.insert(NAME_TABLE_TRANSFER, null, values);
            db.close();
            if (insert == DBUtils.checkDBFail) {
                return insert;
            } else {
                // Trừ số tiền chuyển trong tài khoản chuyển tiền

                // Cộng tiền vào tài khoản được nhận tiền

            }
        } catch (Exception e) {
            AppUtils.handlerException(e);
        }
        db.close();
        return DBUtils.checkDBFail;
    }

    /**
     * @param transfer            đối tượng chuyển tiền thay đổi
     * @param numberMoneyPrevious số tiền lúc trước
     * @created_by nxduong on 16/2/2021
     */

    @Override
    public long updateTransfer(Transfer transfer, int numberMoneyPrevious) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(AMOUNT, transfer.getAmount());
        values.put(FROM_ACCOUNT_ID, transfer.getFromAccountId());
        values.put(TO_ACCOUNT_ID, transfer.getToAccountId());
        values.put(DATE, transfer.getDate());
        values.put(TIME, transfer.getTime());
        values.put(EXPLAIN, transfer.getExplain());
        values.put(REPORT, transfer.getReport());
        values.put(IMAGE, transfer.getImage());
        values.put(STATUS, transfer.getStatus());

        try {
            long update = db.update(NAME_TABLE_TRANSFER, values, TRANSFER_ID + " = ? ",
                    new String[]{String.valueOf(transfer.getTransferId())});

        } catch (Exception e) {
            AppUtils.handlerException(e);
        }
        db.close();
        return DBUtils.checkDBFail;
    }

    /**
     * @param transfer đối tượng chuyển tiền muốn xóa
     * @created_by nxduong on 16/2/2021
     */
    @Override
    public long deleteTransfer(Transfer transfer) {
        db = this.getWritableDatabase();
        long delete = DBUtils.checkDBFail;
        try {
            delete = db.delete(NAME_TABLE_TRANSFER, TRANSFER_ID + " = ?",
                    new String[]{String.valueOf(transfer.getTransferId())});

            if (delete == DBUtils.checkDBFail) {
                db.close();
                return delete;
            } else {
                // Cộng lại số tiền chuyển trong tài khoản chuyển tiền

                // Trừ tiền vào tài khoản được nhận tiền


            }
        } catch (Exception e) {
            AppUtils.handlerException(e);
        }

        db.close();
        return delete;
    }
}
