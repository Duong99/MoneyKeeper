package vn.com.nghiemduong.moneykeeper.data.db.user;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import vn.com.nghiemduong.moneykeeper.data.db.BaseSqLite;
import vn.com.nghiemduong.moneykeeper.data.model.db.User;

/**
 * Lớp sử lý database thêm sửa xóa cho bảng user
 * - @created_by nxduong on 26/1/2021
 **/

public class UserMoneyDatabase extends BaseSqLite {

    private final static String NAME_TABLE_USER = "tb_User";
    private final static String USER_ID = "userId";
    private final static String USER_EMAIL = "email";

    private SQLiteDatabase mDB;

    public UserMoneyDatabase(@Nullable Context context) {
        super(context);
    }

    public void insertUser(User user) {
        mDB = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_EMAIL, user.getEmail());
        long insert = mDB.insert(NAME_TABLE_USER, null, values);
    }
}
