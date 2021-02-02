package vn.com.nghiemduong.moneykeeper.data.db.user;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import vn.com.nghiemduong.moneykeeper.data.model.User;
import vn.com.nghiemduong.moneykeeper.utils.DBUtils;

/**
 * Lớp sử lý database thêm sửa xóa cho bảng user
 * - @created_by nxduong on 26/1/2021
 **/

public class UserMoneyDatabase extends SQLiteOpenHelper implements UserMoneyDatabaseMvpView {

    private final static String NAME_TABLE_USER = "tb_User";
    private final static String USER_ID = "userId";
    private final static String USER_EMAIL = "email";

    private SQLiteDatabase mDB;

    public UserMoneyDatabase(@Nullable Context context) {
        super(context, DBUtils.DB_NAME, null, DBUtils.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertUser(User user) {
        mDB = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_EMAIL, user.getEmail());
        long insert = mDB.insert(NAME_TABLE_USER, null, values);
    }
}
