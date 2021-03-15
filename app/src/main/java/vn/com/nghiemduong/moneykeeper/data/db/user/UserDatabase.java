package vn.com.nghiemduong.moneykeeper.data.db.user;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.google.android.gms.common.api.internal.LifecycleFragment;

import java.util.ArrayList;

import vn.com.nghiemduong.moneykeeper.data.db.BaseSqLite;
import vn.com.nghiemduong.moneykeeper.data.model.db.User;
import vn.com.nghiemduong.moneykeeper.utils.AppUtils;
import vn.com.nghiemduong.moneykeeper.utils.DBUtils;

/**
 * Lớp sử lý database thêm sửa xóa cho bảng user
 * - @created_by nxduong on 26/1/2021
 **/

public class UserDatabase extends BaseSqLite implements UserDatabaseMvpPresenter {

    private final static String NAME_TABLE_USER = "tb_User";
    private final static String USER_ID = "userId";
    private final static String NAME = "name";
    private final static String EMAIL = "email";
    private final static String PHONE = "phone";
    private final static String BIRTHDAY = "birthday";
    private final static String GENDER = "gender";
    private final static String PASSWORD = "password";

    private SQLiteDatabase db;

    public UserDatabase(@Nullable Context context) {
        super(context);
    }

    @Override
    public ArrayList<User> getAllUsers() {
        db = this.getReadableDatabase();
        ArrayList<User> listUsers = new ArrayList<>();
        String query = "SELECT * FROM " + NAME_TABLE_USER;

        try {
            @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                do {
                    User user = new User(cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getString(4),
                            cursor.getInt(5),
                            cursor.getString(6));
                    listUsers.add(user);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            AppUtils.handlerException(e);
        }

        db.close();
        return listUsers;
    }

    @Override
    public User getUser(String email) {
        db = this.getReadableDatabase();
        User user = null;
        String query = "SELECT * FROM " + NAME_TABLE_USER + " WHERE " + EMAIL + " = '" + email + "'";
        try {
            @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    user = new User(cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getString(4),
                            cursor.getInt(5),
                            cursor.getString(6));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            AppUtils.handlerException(e);
        }
        db.close();
        return user;
    }

    /**
     * -  HÀm thêm tài khoản người dùng
     *
     * @param user đối tượng người dùng
     *             - @created_by nxduong on 15/3/2021
     **/
    public long insertUser(User user) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, user.getName());
        values.put(EMAIL, user.getEmail());
        values.put(PHONE, user.getNumberPhone());
        values.put(BIRTHDAY, user.getBirthday());
        values.put(GENDER, user.getGender());
        values.put(PASSWORD, user.getPassword());
        long insert = DBUtils.checkDBFail;

        try {
            insert = db.insert(NAME_TABLE_USER, null, values);
        } catch (Exception e) {
            AppUtils.handlerException(e);
        }

        db.close();
        return insert;
    }

    /**
     * -  Hàm cập nhật tài khoản người dùng
     * - @created_by nxduong on 15/3/2021
     **/

    @Override
    public long updateUser(User user) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, user.getName());
        values.put(EMAIL, user.getEmail());
        values.put(PHONE, user.getNumberPhone());
        values.put(BIRTHDAY, user.getEmail());
        values.put(GENDER, user.getGender());
        values.put(PASSWORD, user.getPassword());
        long update = DBUtils.checkDBFail;
        try {
            update = db.update(NAME_TABLE_USER, values, "WHERE " + USER_ID + " = ?"
                    , new String[]{String.valueOf(user.getUserId())});
        } catch (Exception e) {
            AppUtils.handlerException(e);
        }
        db.close();
        return update;

    }

    /**
     * -  Hàm xóa tài khoản người dùng
     * - @created_by nxduong on 15/3/2021
     **/
    @Override
    public long deleteUser(User user) {
        db = this.getWritableDatabase();
        long delete = DBUtils.checkDBFail;

        try {
            delete = db.delete(NAME_TABLE_USER, "WHERE " + USER_ID + " = ?",
                    new String[]{String.valueOf(user.getUserId())});
        } catch (Exception e) {
            AppUtils.handlerException(e);
        }
        return delete;
    }
}
